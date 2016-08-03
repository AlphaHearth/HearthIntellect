package com.hearthintellect.crawler.crawler;

import com.hearthintellect.model.Card;
import com.hearthintellect.model.CardQuote;
import com.hearthintellect.model.HeroClass;
import com.hearthintellect.model.Mechanic;
import com.hearthintellect.util.LocaleString;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public class CardCrawler {
    private static final Logger LOG = LoggerFactory.getLogger(CardCrawler.class);

    private static final String HEARTH_HEAD_URL = "http://www.hearthhead.com/card=%d";
    private static Locale locale = new Locale("en", "US");
    private static final String EFFECTIVE_ID = "D:\\GithubRepository\\HearthIntellect\\resources\\effectiveHHID.txt";
    private static final String CARD_JSON_URL = "D:\\GithubRepository\\HearthIntellect\\resources\\hearthheadCards.json";
    private static final String MECHANIC_JSON_URL = "D:\\GithubRepository\\HearthIntellect\\resources\\hearthheadMechanics.json";

    private static final int[] patches = { 3140, 3388, 3604, 3645, 3664, 3749, 3890, 3937, 4217, 4217, 4243, 4442,
        4458, 4482, 4944, 4973, 5170, 5314, 5435, 5506, 5834, 6024, 6141, 6284, 6485, 6898, 7234, 7628, 7785, 7835,
        8036, 8108, 8311, 8416, 8834, 9166, 9554, 9786, 10357, 10604, 10784, 10833, 10956, 11461, 11959, 12051,
        12105, 12266, 12574, 13030, 13619, 13714, 13740, 13807 };

    private static final Map<Integer, Card.Quality> QUALITY_MAP = new HashMap<>();
    private static final Map<Integer, Card.Type> TYPE_MAP = new HashMap<>();
    private static final Map<Integer, Card.Set> SET_MAP = new HashMap<>();
    private static final Map<Integer, Card.Race> RACE_MAP = new HashMap<>();
    private static final Map<Integer, HeroClass> CLASS_MAP = new HashMap<>();

    static {
        QUALITY_MAP.put(0, Card.Quality.Free);
        QUALITY_MAP.put(1, Card.Quality.Common);
        QUALITY_MAP.put(3, Card.Quality.Rare);
        QUALITY_MAP.put(4, Card.Quality.Epic);
        QUALITY_MAP.put(5, Card.Quality.Legendary);

        TYPE_MAP.put(4, Card.Type.Minion);
        TYPE_MAP.put(5, Card.Type.Spell);
        TYPE_MAP.put(10, Card.Type.HeroPower);
        TYPE_MAP.put(3, Card.Type.Hero);
        TYPE_MAP.put(7, Card.Type.Weapon);

        SET_MAP.put(2, Card.Set.Basic);
        SET_MAP.put(3, Card.Set.Classic);
        SET_MAP.put(4, Card.Set.Reward);
        SET_MAP.put(5, Card.Set.Missions);
        SET_MAP.put(11, Card.Set.Promotion);
        SET_MAP.put(12, Card.Set.Naxxramas);
        SET_MAP.put(13, Card.Set.GoblinsVsGnomes);
        SET_MAP.put(14, Card.Set.BlackrockMountain);
        SET_MAP.put(15, Card.Set.TheGrandTournament);
        SET_MAP.put(16, Card.Set.Credits);
        SET_MAP.put(17, Card.Set.AlternativeHeros);
        SET_MAP.put(18, Card.Set.TavernBrawl);
        SET_MAP.put(20, Card.Set.LeagueOfExplorers);
        SET_MAP.put(21, Card.Set.WhisperOfTheOldGods);

        RACE_MAP.put(20, Card.Race.Beast);
        RACE_MAP.put(24, Card.Race.Dragon);
        RACE_MAP.put(14, Card.Race.Murloc);
        RACE_MAP.put(17, Card.Race.Mech);
        RACE_MAP.put(15, Card.Race.Demon);
        RACE_MAP.put(21, Card.Race.Totem);
        RACE_MAP.put(23, Card.Race.Pirate);

        CLASS_MAP.put(2, HeroClass.Paladin);
        CLASS_MAP.put(1, HeroClass.Warlock);
        CLASS_MAP.put(3, HeroClass.Hunter);
        CLASS_MAP.put(7, HeroClass.Shaman);
        CLASS_MAP.put(4, HeroClass.Rogue);
        CLASS_MAP.put(11, HeroClass.Druid);
        CLASS_MAP.put(8, HeroClass.Mage);
        CLASS_MAP.put(9, HeroClass.Warlock);
        CLASS_MAP.put(5, HeroClass.Priest);
    }

    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");

        LOG.info("Reading card JSON from `{}`", CARD_JSON_URL);
        String json = readFile(CARD_JSON_URL);
        JSONArray jsonCards = new JSONArray(json);

        LOG.info("Parsing card JSON...");
        List<Card> cards = parseCards(jsonCards);
        LOG.info("Fetched {} cards.", cards.size());

        LOG.info("Reading mechanic JSON from `{}`", MECHANIC_JSON_URL);
        json = readFile(MECHANIC_JSON_URL);
        JSONArray jsonMechanics = new JSONArray(json);

        LOG.info("Parsing mechanic JSON...");
        List<Mechanic> mechanics = parseMechanics(jsonMechanics);
        LOG.info("Fetched {} mechanics.", mechanics.size());

        mechanics.sort((m1, m2) -> Integer.compare(m1.getHHID(), m2.getHHID()));

        LOG.info("Sorting cards on image url...");
        cards.sort((c1, c2) -> Integer.compare(c1.getHHID(), c2.getHHID()));

        LOG.info("Reading effective HHID...");
        List<Integer> effectiveHHIDs = readHHID(EFFECTIVE_ID);

        for (Integer hhid : effectiveHHIDs) {
            try {
                LOG.info("Crawling HHID={}...", hhid);

                Card card = searchCard(cards, hhid);
                if (card == null) {
                    LOG.warn("Failed to find card for HHID `{}`.", hhid);
                    continue;
                }

                Document doc = Jsoup.connect(String.format(HEARTH_HEAD_URL, hhid))
                                   .timeout(0).userAgent("Mozilla").get();

                LOG.info("Parsing Mechanics...");
                // Extract information
                Element script = doc.select("#main-contents").select("noscript ~ script").first();
                String[] strs = script.html().split(";\n");
                String mechanicString = null;
                for (String str : strs) {
                    if (str.startsWith("var lv_mechanics")) {
                        mechanicString = str;
                        break;
                    }
                }

                // Parse Mechanics
                String mechanicJson = mechanicString.split("=")[1].trim();
                List<Mechanic> cardMechanics = new ArrayList<>();
                card.setMechanics(cardMechanics);
                JSONArray mechanicArr = new JSONArray(mechanicJson);
                for (int i = 0; i < mechanicArr.length(); i++) {
                    int id = mechanicArr.getJSONObject(i).getInt("id");
                    Mechanic mechanic = searchMechanic(mechanics, id);
                    cardMechanics.add(mechanic);
                }

                LOG.info("Parsing Quotes for `{}`...", card.getName().get(locale));
                // Parse Quote
                Elements audios = doc.select("#sounds > audio");
                if (audios.isEmpty())
                    continue;
                script = doc.select("#sounds ~ script").first();
                String jScript = script.html();
                // LOG.info(jScript);
                for (Element audio : audios) {
                    // LOG.info(audio.outerHtml());
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
                    // LOG.info(jScript.substring(b + 1, c));
                    int d = jScript.indexOf("<i>", b);
                    if (d > b && d < c) {
                        int e = jScript.indexOf("</i>", d);
                        String quoteStr = jScript.substring(d + 3, e);
                        LOG.info("{} : {} - {}", aText, quoteStr, link);
                        if (quote.getLine() == null)
                            quote.setLine(new LocaleString());
                        quote.getLine().put(locale, quoteStr);
                    } else
                        LOG.info("{} - {}", aText, link);
                }
            } catch (Throwable ex) {
                LOG.error("Aoh: ", ex);
            }
        }

        LOG.info("Sorting cards...");
        cards.sort((c1, c2) -> {
            if (c1.getSet() != c2.getSet())
                return c1.getSet().ordinal() - c2.getSet().ordinal();
            if (c1.getHeroClass() != c2.getHeroClass())
                return c1.getHeroClass().ordinal() - c2.getHeroClass().ordinal();
            return c1.getCost() - c2.getCost();
        });

        LOG.info("Assigning IDs...");
        for (int i = 0; i < cards.size(); i++)
            cards.get(i).setId(i + 1);

        LOG.info("Saving to database...");
    }

    private static List<Mechanic> parseMechanics(JSONArray jsonMechanics) {
        List<Mechanic> mechanics = new ArrayList<>(jsonMechanics.length());

        for (int i = 0; i < jsonMechanics.length(); i++) {
            JSONObject jsonMechanic = jsonMechanics.getJSONObject(i);
            Mechanic mechanic = new Mechanic();
            mechanic.setId(jsonMechanic.getInt("_id"));
            mechanic.setHHID(jsonMechanic.getInt("HHID"));
            LocaleString description = new LocaleString();
            description.put(locale, jsonMechanic.getString("description"));
            mechanic.setDescription(description);
            mechanics.add(mechanic);
        }

        return mechanics;
    }

    private static Mechanic searchMechanic(List<Mechanic> mechanics, int hhid) {
        if (mechanics.isEmpty())
            return null;
        int i = 0;
        int j = mechanics.size();
        int idx;
        while (i <= j) {
            idx = (i + j) / 2;
            Mechanic mechanic = mechanics.get(idx);
            if (mechanic.getHHID() == hhid)
                return mechanic;
            else if (mechanic.getHHID() < hhid)
                i = idx + 1;
            else
                j = idx - 1;
        }
        return null;
    }

    private static Card searchCard(List<Card> cards, int hhid) {
        if (cards.isEmpty())
            return null;
        int i = 0;
        int j = cards.size();
        int idx;
        while (i <= j) {
            idx = (i + j) / 2;
            Card card = cards.get(idx);
            if (card.getHHID() == hhid)
                return card;
            else if (card.getHHID() < hhid)
                i = idx + 1;
            else
                j = idx - 1;
        }
        return null;
    }

    private static List<Integer> readHHID(String effectiveId) throws IOException {
        String content = readFile(effectiveId);
        String[] idStrs = content.split(", ");
        List<Integer> results = new ArrayList<>();
        for (String idStr : idStrs)
            results.add(Integer.valueOf(idStr.trim()));
        return results;
    }

    private static String readFile(String url) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(url))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
        }
        return builder.toString();
    }

    private static List<Card> parseCards(JSONArray jsonArr) {
        List<Card> cards = new ArrayList<>(jsonArr.length());
        Set<Integer> hhid = new HashSet<>();

        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject cardJson = jsonArr.getJSONObject(i);
            Card card = new Card();

            LOG.debug("Parsing card {} ...", cardJson.getString("name"));

            try {
                LocaleString name = new LocaleString();
                name.put(locale, cardJson.getString("name"));
                card.setName(name);

                card.setHHID(cardJson.getInt("id"));

                if (cardJson.has("cost"))
                    card.setCost(cardJson.getInt("cost"));
                if (cardJson.has("attack"))
                    card.setAttack(cardJson.getInt("attack"));
                if (cardJson.has("health"))
                    card.setHealth(cardJson.getInt("health"));
                else if (cardJson.has("durability"))
                    card.setHealth(cardJson.getInt("durability"));

                if (cardJson.has("description")) {
                    LocaleString effect = new LocaleString();
                    effect.put(locale, cardJson.getString("description"));
                    card.setEffect(effect);
                }

                if (cardJson.has("collectible") && cardJson.getInt("collectible") == 1)
                    card.setCollectible(true);

                if (cardJson.has("quality"))
                    card.setQuality(QUALITY_MAP.get(cardJson.getInt("quality")));
                else
                    card.setQuality(Card.Quality.Free);

                card.setType(TYPE_MAP.get(cardJson.getInt("type")));
                if (card.getType() == Card.Type.Minion) {
                    if (cardJson.has("race"))
                        card.setRace(RACE_MAP.get(cardJson.getInt("race")));
                    else
                        card.setRace(Card.Race.None);
                }
                card.setSet(SET_MAP.get(cardJson.getInt("set")));
                if (cardJson.has("classs"))
                    card.setHeroClass(CLASS_MAP.get(cardJson.getInt("classs")));
                else
                    card.setHeroClass(HeroClass.Neutral);

                card.setImageUrl(cardJson.getString("image"));

                if (!hhid.contains(card.getHHID())) {
                    hhid.add(card.getHHID());
                    cards.add(card);
                }
            } catch (Throwable ex) {
                LOG.warn("Catch error: ", ex);
            }
        }

        return cards;
    }

}
