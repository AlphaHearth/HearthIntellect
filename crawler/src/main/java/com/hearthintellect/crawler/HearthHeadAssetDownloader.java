package com.hearthintellect.crawler;

import com.hearthintellect.config.SpringMongoConfig;
import com.hearthintellect.dao.CardRepository;
import com.hearthintellect.model.Card;
import com.hearthintellect.model.CardQuote;
import com.hearthintellect.utils.ConcurrentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HearthHeadAssetDownloader {
    private static final Logger LOG = LoggerFactory.getLogger(HearthHeadAssetDownloader.class);

    private static final Locale[] DOWNLOAD_LOCALES = {
        new Locale("en", "US")
    };

    private static Map<Locale, Path> imageDests;
    private static Map<Locale, Path> soundDests;

    public static void main(String[] args) throws IOException, InterruptedException {
        Path downloadDest = Constants.ASSETS_PATH;
        Files.createDirectories(downloadDest);
        // Recursively delete the directory
        Files.walkFileTree(downloadDest, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
        Files.createDirectory(downloadDest);
        Path imageDest = downloadDest.resolve("img");
        Files.createDirectory(imageDest);
        imageDests = new HashMap<>();
        for (Locale locale : DOWNLOAD_LOCALES) {
            Path localeImageDest = imageDest.resolve(toPath(locale));
            Files.createDirectories(localeImageDest);
            imageDests.put(locale, localeImageDest);
        }
        Path soundDest = downloadDest.resolve("sound");
        Files.createDirectory(soundDest);
        soundDests = new HashMap<>();
        for (Locale locale : DOWNLOAD_LOCALES) {
            Path localeSoundDest = soundDest.resolve(toPath(locale));
            Files.createDirectories(localeSoundDest);
            soundDests.put(locale, localeSoundDest);
        }

        LOG.info("Initializing link to database...");
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringMongoConfig.class);

        LOG.info("Reading Card entities from database...");
        CardRepository cardRepository = context.getBean(CardRepository.class);
        Iterator<Card> iter = cardRepository.findAll();
        List<String> imageIds = new LinkedList<>();
        List<String> soundIds = new LinkedList<>();
        while (iter.hasNext()) {
            Card card = iter.next();
            String imageUrl = card.getImageUrl();
            if (imageUrl != null && !imageUrl.trim().isEmpty())
                imageIds.add(imageUrl);
            for (CardQuote quote : card.getQuotes()) {
                String audioUrl = quote.getAudioUrl();
                if (audioUrl != null && !audioUrl.trim().isEmpty())
                    soundIds.add(audioUrl);
            }
        }

        LOG.info("Downloading...");
        ExecutorService executor = Executors.newFixedThreadPool(50);
        for (String imageId : imageIds)
            executor.submit(() -> downloadCardImage(imageId));
        for (String soundId : soundIds)
            executor.submit(() -> downloadCardSound(soundId));
        ConcurrentUtils.shutdownAndWait(executor);

        LOG.info("Finished!");
    }

    private static void downloadCardImage(String cardImageId) {
        for (Locale locale : DOWNLOAD_LOCALES) {
            // Download medium
            String urlStr = Constants.hearthheadCardImageUrl(cardImageId, Constants.ImageType.Medium, locale);
            Path dest = imageDests.get(locale).resolve(cardImageId + "_medium.png");
            downloadFile(urlStr, dest);

            // Download original
            urlStr = Constants.hearthheadCardImageUrl(cardImageId, Constants.ImageType.Original, locale);
            dest = imageDests.get(locale).resolve(cardImageId + "_original.png");
            downloadFile(urlStr, dest);

            // Download premium
            urlStr = Constants.hearthheadCardImageUrl(cardImageId, Constants.ImageType.Premium, locale);
            dest = imageDests.get(locale).resolve(cardImageId + "_premium.gif");
            downloadFile(urlStr, dest);
        }
    }

    private static void downloadCardSound(String cardSoundId) {
        for (Locale locale : DOWNLOAD_LOCALES) {
            // Download OGG
            String urlStr = Constants.hearthheadCardSoundUrl(cardSoundId, false, locale);
            Path dest = soundDests.get(locale).resolve(cardSoundId + ".ogg");
            downloadFile(urlStr, dest);
        }
    }

    private static void downloadFile(String remoteUrl, Path destPath) {
        URL url;
        try {
            url = new URL(remoteUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() != 200) {
                LOG.warn("URL `{}` return response code `{}`.", remoteUrl, conn.getResponseCode());
                return;
            }
            if (LOG.isDebugEnabled())
                LOG.debug("Downloading `{}` to `{}`...", remoteUrl, destPath.toRealPath());
            Files.copy(conn.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);
            LOG.info("Downloaded `{}` to `{}`.", remoteUrl, destPath.toRealPath());
        } catch (MalformedURLException ex) {
            LOG.warn("URL `{}` is in invalid form.", remoteUrl);
        } catch (IOException ex) {
            LOG.warn("Exception occurred when trying to download from " + remoteUrl, ex);
        }
    }

    private static String toPath(Locale locale) {
        return String.format("%s%s", locale.getLanguage(), locale.getCountry().toLowerCase());
    }
}
