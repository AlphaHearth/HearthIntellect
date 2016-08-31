package com.hearthintellect.crawler.crawler;

import com.hearthintellect.model.Card;
import com.hearthintellect.model.CardQuote;
import com.hearthintellect.model.Mechanic;
import com.hearthintellect.util.LocaleString;
import com.hearthintellect.utils.CollectionUtils;
import com.hearthintellect.utils.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HearthHeadCardCrawler {
    private static final Logger LOG = LoggerFactory.getLogger(HearthHeadCardCrawler.class);

    private static final String HEARTH_HEAD_URL = "D:\\GithubRepository\\HearthIntellect\\resources\\hh\\%d.html";
    private static final String EFFECTIVE_ID = "D:\\GithubRepository\\HearthIntellect\\resources\\effectiveHHID.txt";

    public static void crawl(List<Card> cards, List<Mechanic> mechanics)
        throws IOException {

        LOG.info("Reading effective HHID...");
        List<Integer> effectiveHHIDs = readHHID(EFFECTIVE_ID);

        LOG.info("Sorting mechanics on HHID...");
        mechanics.sort((m1, m2) -> Integer.compare(m1.getHHID(), m2.getHHID()));

        LOG.info("Sorting cards on HHID...");
        cards.sort((c1, c2) -> Integer.compare(c1.getHHID(), c2.getHHID()));
        for (Integer hhid : effectiveHHIDs) {
            crawlCard(cards, mechanics, hhid);
        }
    }

    private static void crawlCard(List<Card> cards, List<Mechanic> mechanics, int hhid) {
        try {
            LOG.info("Crawling HHID={}...", hhid);

            Card card = CollectionUtils.binarySearch(cards, hhid, Card::getHHID);
            if (card == null) {
                LOG.warn("Failed to find card for HHID `{}`.", hhid);
                return;
            }

            LOG.debug("Found card `{}`.", card.getName().get(CardCrawler.DEFAULT_LOCALE));

            Document doc = null;
            try {
                doc = Jsoup.parse(IOUtils.readFile(String.format(HEARTH_HEAD_URL, hhid)));
            } catch (IOException ex) {
                LOG.error("Failed to open html file for `" + hhid + "`", ex);
                return;
            }

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
            if (audios.isEmpty()) {
                // printCard(card);
                return;
            }
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
            // printCard(card);
        } catch (Throwable ex) {
            LOG.error("Ah oh: ", ex);
        }
    }

    private static void printCard(Card card) {
        JSONObject cardJson = new JSONObject();
        cardJson.put("HHID", card.getHHID());
        cardJson.put("name", card.getName().get(CardCrawler.DEFAULT_LOCALE));
        cardJson.put("effect", card.getEffect().get(CardCrawler.DEFAULT_LOCALE));
        cardJson.put("description", card.getDesc().get(CardCrawler.DEFAULT_LOCALE));
        cardJson.put("cost", card.getCost());
        cardJson.put("type", card.getType().name().toUpperCase());
        cardJson.put("quality", card.getQuality().name().toUpperCase());
        cardJson.put("set", card.getSet().name().toUpperCase());
        cardJson.put("playerClass", card.getHeroClass().name().toUpperCase());
        cardJson.put("image", card.getImageUrl());
        if (card.getType() == Card.Type.Minion || card.getType() == Card.Type.Weapon) {
            cardJson.put("attack", card.getAttack());
            cardJson.put("health", card.getHealth());
        }

        JSONArray mechanics = new JSONArray();
        for (Mechanic mechanic : card.getMechanics()) {
            mechanics.put(mechanic.getId());
        }
        cardJson.put("mechanics", mechanics);

        JSONArray quotes = new JSONArray();
        for (CardQuote quote : card.getQuotes()) {
            JSONObject quoteJson = new JSONObject();
            quoteJson.put("type", quote.getType().name().toUpperCase());
            quoteJson.put("link", quote.getAudioUrl());
            if (quote.getLine() != null)
                quoteJson.put("line", quote.getLine().get(CardCrawler.DEFAULT_LOCALE));
            quotes.put(quoteJson);
        }
        cardJson.put("quotes", quotes);

        LOG.info(cardJson.toString());
    }

    private static List<Integer> readHHID(String effectiveId) throws IOException {
        String content = IOUtils.readFile(effectiveId);
        String[] idStrs = content.split(",");
        List<Integer> results = new ArrayList<>();
        for (String idStr : idStrs)
            results.add(Integer.valueOf(idStr.trim()));
        return results;
    }

}
