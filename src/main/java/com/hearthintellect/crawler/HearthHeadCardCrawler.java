package com.hearthintellect.crawler;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hearthintellect.config.SpringMongoConfig;
import com.hearthintellect.model.Card;
import com.hearthintellect.model.CardQuote;
import com.hearthintellect.repository.CardRepository;
import com.hearthintellect.utils.ConcurrentUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HearthHeadCardCrawler {
    private static final Logger LOG = LoggerFactory.getLogger(HearthHeadCardCrawler.class);

    private static final String DATA_SCRIPT_PREFIX = "window.HearthHeadData =";

    private static final Gson gson = new Gson();
    private static final Map<String, CardQuote.Type> quoteTypeMap = new HashMap<>();
    static {
        quoteTypeMap.put("ATTACK_SOUND", CardQuote.Type.Attack);
        quoteTypeMap.put("DEATH_SOUND", CardQuote.Type.Death);
        quoteTypeMap.put("PLAY_SOUND", CardQuote.Type.Play);
        quoteTypeMap.put("TRIGGER_SOUND", CardQuote.Type.Trigger);
        quoteTypeMap.put("OTHER_SOUND", CardQuote.Type.Other);
        quoteTypeMap.put("ALTERNATE_SOUND", CardQuote.Type.Alternate);
    }

    public static void main(String[] args) throws InterruptedException {
        LOG.info("Initializing link to database...");
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringMongoConfig.class);

        LOG.info("Reading Cards from database...");
        CardRepository cardRepository = context.getBean(CardRepository.class);
        Iterable<Card> cards = cardRepository.findAll();

        LOG.info("Crawling cards from HearthHead...");
        ExecutorService executor = Executors.newFixedThreadPool(50);
        for (Card card : cards)
            executor.submit(() -> crawlCard(card));

        ConcurrentUtils.shutdownAndWait(executor);

        LOG.info("Inserting new data to database...");
        cardRepository.save(cards);
    }

    private static void crawlCard(Card card) {
        try {
            String cardName = card.getName().get(Constants.DEFAULT_LOCALE);
            String hhid = cardName.toLowerCase().replace("\"", "").replace(' ', '-').replace("\'", "")
                    .replace("!", "").replace(",", "").replace(":", "");
            LOG.debug("Crawling card `{}` with HHID=`{}`...", cardName, hhid);

            String url = Constants.hearthheadCardUrl(hhid);
            Document doc;
            try {
                doc = Jsoup.connect(url).timeout(0).get();
            } catch (HttpStatusException ex) {
                LOG.error("Failed to find card page `{}`.", url);
                LOG.error("Failed to deduce corresponding HearthHead ID for card `{}`.", cardName);
                return;
            }

            Element mainContentSection = doc.getElementById("main-content");
            Element dataScript = mainContentSection.getElementsByTag("script").first();
            if (!dataScript.html().contains(DATA_SCRIPT_PREFIX)) {
                LOG.error("For card `{}` with HHID=`{}`, text of first script element in main content section is:"
                        + "\n{}\n, which does not contain the designated prefix.",
                        cardName, hhid, dataScript.html());
                return;
            }

            JsonObject jsonObject = gson.fromJson(
                    dataScript.html().substring(dataScript.html().indexOf(DATA_SCRIPT_PREFIX) + DATA_SCRIPT_PREFIX.length()),
                    JsonObject.class
            );
            JsonArray mediaArray = jsonObject.get("card").getAsJsonObject().get("media").getAsJsonArray();

            List<CardQuote> cardQuotes = new ArrayList<>(5);
            for (int i = 0; i < mediaArray.size(); i++) {
                JsonObject mediaObject = mediaArray.get(i).getAsJsonObject();
                CardQuote.Type quoteType = quoteTypeMap.get(mediaObject.get("type").getAsString());
                if (quoteType != null) {
                    String audioUrl = mediaObject.get("url").getAsString();
                    String[] a = audioUrl.split("/");
                    if (a.length != 5) {
                        LOG.error("Audio URL with unexpected pattern: " + audioUrl);
                        System.exit(1);
                    }
                    String[] b = a[4].split("\\.");
                    if (b.length != 2) {
                        LOG.error("Audio URL with unexpected pattern: " + audioUrl);
                        System.exit(1);
                    }
                    CardQuote cardQuote = new CardQuote(quoteType, null, b[0]);
                    cardQuotes.add(cardQuote);
                }
            }
            card.setQuotes(cardQuotes);
            LOG.info("Fetched {} card quotes for card `{}` with HHID=`{}`.", cardQuotes.size(), cardName, hhid);
        } catch (Exception ex) {
            LOG.error("Ah oh: ", ex);
        }
    }
}
