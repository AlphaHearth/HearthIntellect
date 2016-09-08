package com.hearthintellect.crawler;

import com.hearthintellect.model.Card;
import com.hearthintellect.model.CardQuote;
import com.hearthintellect.model.Mechanic;
import com.hearthintellect.utils.IOUtils;
import com.hearthintellect.utils.LocaleString;
import com.hearthintellect.utils.CollectionUtils;
import com.hearthintellect.utils.ConcurrentUtils;
import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HearthHeadCardCrawler {
    private static final Logger LOG = LoggerFactory.getLogger(HearthHeadCardCrawler.class);

    public static void crawl(List<Card> cards, List<Mechanic> mechanics)
        throws IOException {

        LOG.info("Detecting effective HHID...");
        List<Integer> effectiveHHIDs = detectHHID();
        LOG.info("Found {} effective HHID.", effectiveHHIDs.size());
        effectiveHHIDs.sort(Integer::compare);

        LOG.info("Sorting mechanics on HHID...");
        mechanics.sort((m1, m2) -> Integer.compare(m1.getHHID(), m2.getHHID()));

        LOG.info("Crawling cards from HearthHead...");
        ExecutorService executor = Executors.newFixedThreadPool(50);
        for (Integer hhid : effectiveHHIDs)
            executor.submit(() -> crawlCard(cards, mechanics, hhid));
        try {
            ConcurrentUtils.shutdownAndWait(executor);
        } catch (InterruptedException ex) {}
    }

    private static void crawlCard(List<Card> cards, List<Mechanic> mechanics, int hhid) {
        try {
            LOG.debug("Crawling HHID={}...", hhid);

            URL url = new URL(Constants.hearthheadCardUrl(hhid));
            URLConnection conn = IOUtils.openConnWithRetry(url, 5, 500);
            String content;
            try (Scanner scanner = new Scanner(conn.getInputStream()).useDelimiter("\\A")) {
                content = scanner.hasNext() ? scanner.next() : "";
            }

            Document doc = Jsoup.parse(content);

            LOG.debug("Parsing image url...");
            Element imageMeta = doc.select("meta[property=twitter:image]").first();
            String imageUrl = imageMeta.attr("content");

            String[] x = imageUrl.split("\\.");
            String y = x[x.length - 2];
            String[] z = y.split("/");
            String hjId = z[z.length - 1];

            Card card = CollectionUtils.binarySearch(cards, hjId, Card::getImageUrl);
            if (card == null) {
                LOG.warn("Failed to find card for Image Url `{}`. Current hhid = {}", hjId, hhid);
                return;
            }
            card.setHHID(hhid);

            LOG.debug("Found card `{}`.", card.getName().get(CardCrawler.DEFAULT_LOCALE));

            LOG.debug("Parsing Mechanics...");
            // Extract information
            Elements scripts = doc.select("#main-contents > script");
            String mechanicString = null;
            for (Element script : scripts) {
                String[] strs = script.html().split(";\n");
                for (String str : strs) {
                    if (str.startsWith("var lv_mechanics")) {
                        mechanicString = str;
                        break;
                    }
                }
                if (mechanicString != null)
                    break;
            }

            // Parse Mechanics
            String mechanicJson = mechanicString.split("=")[1].trim();
            List<Mechanic> cardMechanics = new ArrayList<>();
            card.setMechanics(cardMechanics);
            JSONArray mechanicArr = new JSONArray(mechanicJson);
            for (int i = 0; i < mechanicArr.length(); i++) {
                int id = mechanicArr.getJSONObject(i).getInt("id");
                Mechanic mechanic = CollectionUtils.binarySearch(mechanics, id, Mechanic::getHHID);
                cardMechanics.add(mechanic);
            }

            LOG.debug("Parsing Quotes...", card.getName().get(CardCrawler.DEFAULT_LOCALE));
            // Parse Quote
            Elements audios = doc.select("#sounds > audio");
            if (audios.isEmpty())
                return;
            Element script = doc.select("#sounds ~ script").first();
            String jScript = script.html();
            List<CardQuote> quotes = new ArrayList<>(audios.size());
            for (Element audio : audios) {
                CardQuote quote = new CardQuote();
                String id = audio.id();
                String link = audio.select("source[type=audio/mpeg]").first().attr("src").split("/")[5].split("\\.")[0];

                String aText = doc.select("#" + id + " ~ a").first().text();
                switch (aText) {
                    case "Play":
                        quote.setType(CardQuote.Type.Play);
                        break;
                    case "Attack":
                        quote.setType(CardQuote.Type.Attack);
                        break;
                    case "Death":
                        quote.setType(CardQuote.Type.Death);
                        break;
                    case "Alternate":
                        quote.setType(CardQuote.Type.Alternate);
                        break;
                    default:
                        quote.setType(CardQuote.Type.Other);
                }

                int a = jScript.indexOf(id);
                int b = jScript.indexOf('\n', a);
                int c = jScript.indexOf(';', b);
                int d = jScript.indexOf("<i>", b);
                if (d > b && d < c) {
                    int e = jScript.indexOf("</i>", d);
                    String quoteStr = jScript.substring(d + 3, e);
                    LOG.debug("{} : {} - {}", aText, quoteStr, link);
                    if (quote.getLine() == null)
                        quote.setLine(new LocaleString());
                    quote.getLine().put(CardCrawler.DEFAULT_LOCALE, quoteStr);
                } else
                    LOG.debug("{} - {}", aText, link);
                quote.setAudioUrl(link);
                quotes.add(quote);
            }
            card.setQuotes(quotes);
        } catch (Throwable ex) {
            LOG.error("Ah oh: ", ex);
        }
    }

    private static List<Integer> detectHHID() throws IOException {
        List<Integer> results = Collections.synchronizedList(new ArrayList<>(2010));
        ExecutorService executor = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 50000; i++) {
            final int finalI = i;
            executor.submit(() -> {
                try {
                    URL url = new URL(Constants.hearthheadCardUrl(finalI));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("User-Agent", "Mozilla");
                    conn.setRequestMethod("HEAD");
                    if (conn.getResponseCode() == 200) {
                        LOG.debug("Detected effective HHID {}.", finalI);
                        results.add(finalI);
                    }
                } catch (Exception ex) {
                    LOG.error("Error occurred when requesting i = " + finalI, ex);
                }
            });
        }
        try {
            ConcurrentUtils.shutdownAndWait(executor);
        } catch (InterruptedException ex) {}
        return new ArrayList<>(results);
    }

}
