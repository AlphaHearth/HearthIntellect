package com.hearthintellect.crawler;

import com.hearthintellect.model.Card;
import com.hearthintellect.model.Patch;
import com.hearthintellect.utils.ConcurrentUtils;
import com.hearthintellect.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HearthJsonDownloader {
    private static final Logger LOG = LoggerFactory.getLogger(HearthJsonDownloader.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        // Initialize the folder
        Files.deleteIfExists(Constants.HEARTHSTONE_JSON_PATH);
        Files.createDirectories(Constants.HEARTHSTONE_JSON_PATH);

        // Start the download
        ExecutorService executor = Executors.newFixedThreadPool(30);
        for (int patchNum : Constants.PATCHES) {
            executor.submit(() -> {
                String hearthstoneJsonUrl = Constants.hearthstoneJsonUrl(patchNum);
                LOG.info("Downloading patch `{}`...", patchNum);
                try {
                    Path targetFile = targetFile(patchNum);
                    Files.deleteIfExists(targetFile);
                    InputStream input = IOUtils.openConnWithRetry(new URL(hearthstoneJsonUrl), 5, 500);
                    FileUtils.write(targetFile.toFile(), org.apache.commons.io.IOUtils.toString(input, "UTF-8"), "UTF-8");
                    LOG.info("Patch `{}` done!", patchNum);
                } catch (MalformedURLException ex) {
                    LOG.error("Malformed URL: " + hearthstoneJsonUrl);
                } catch (IOException ex) {
                    LOG.error("Exception occurred when trying to download from " + hearthstoneJsonUrl + ": ", ex);
                }
            });
        }
        ConcurrentUtils.shutdownAndWait(executor);
    }

    private static Path targetFile(int patchNumber) {
        return Constants.HEARTHSTONE_JSON_PATH.resolve(String.valueOf(patchNumber) + ".json");
    }
}
