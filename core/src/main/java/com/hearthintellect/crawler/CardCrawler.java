package com.hearthintellect.crawler;

import com.hearthintellect.model.Card;
import com.hearthintellect.model.HeroClass;
import com.hearthintellect.util.LocaleString;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class CardCrawler {
    private static final Logger LOG = LoggerFactory.getLogger(CardCrawler.class);

    private static final String HEARTH_HEAD_URL = "http://www.hearthhead.com/card=%d";
    private static final String TOP_DECK_URL = "http://www.hearthstonetopdecks.com/cards/%s/";

    private static final String HEARTH_JSON_URL = "https://api.hearthstonejson.com/v1/%d/all/cards.json";
    private static final int[] patches = { 3140, 3388, 3604, 3645, 3664, 3749, 3890, 3937, 4217, 4217, 4243, 4442,
        4458, 4482, 4944, 4973, 5170, 5314, 5435, 5506, 5834, 6024, 6141, 6284, 6485, 6898, 7234, 7628, 7785, 7835,
        8036, 8108, 8311, 8416, 8834, 9166, 9554, 9786, 10357, 10604, 10784, 10833, 10956, 11461, 11959, 12051,
        12105, 12266, 12574, 13030, 13619, 13714, 13740, 13807 };

    private static final Map<String, Locale> LOCALE_MAP = new HashMap<>();
    private static final Map<String, Card.Quality> QUALITY_MAP = new HashMap<>();
    private static final Map<String, Card.Type> TYPE_MAP = new HashMap<>();
    private static final Map<String, Card.Set> SET_MAP = new HashMap<>();
    private static final Map<String, Card.Race> RACE_MAP = new HashMap<>();
    private static final Map<String, HeroClass> CLASS_MAP = new HashMap<>();
    static {
        LOCALE_MAP.put("deDE", new Locale("de", "DE"));
        LOCALE_MAP.put("enUS", new Locale("en", "US"));
        LOCALE_MAP.put("esES", new Locale("es", "ES"));
        LOCALE_MAP.put("esMX", new Locale("es", "MX"));
        LOCALE_MAP.put("frFR", new Locale("fr", "FR"));
        LOCALE_MAP.put("itIT", new Locale("it", "IT"));
        LOCALE_MAP.put("jaJP", new Locale("ja", "JP"));
        LOCALE_MAP.put("koKR", new Locale("ko", "KR"));
        LOCALE_MAP.put("plPL", new Locale("pl", "PL"));
        LOCALE_MAP.put("ptBR", new Locale("pt", "BR"));
        LOCALE_MAP.put("ruRU", new Locale("ru", "RU"));
        LOCALE_MAP.put("thTH", new Locale("th", "TH"));
        LOCALE_MAP.put("zhCN", new Locale("zh", "CN"));
        LOCALE_MAP.put("zhTW", new Locale("it", "TW"));

        QUALITY_MAP.put("FREE", Card.Quality.Free);
        QUALITY_MAP.put("COMMON", Card.Quality.Common);
        QUALITY_MAP.put("RARE", Card.Quality.Rare);
        QUALITY_MAP.put("EPIC", Card.Quality.Epic);
        QUALITY_MAP.put("LEGENDARY", Card.Quality.Legendary);

        TYPE_MAP.put("MINION", Card.Type.Minion);
        TYPE_MAP.put("SPELL", Card.Type.Spell);
        TYPE_MAP.put("HERO_POWER", Card.Type.HeroPower);
        TYPE_MAP.put("HERO", Card.Type.Hero);
        TYPE_MAP.put("WEAPON", Card.Type.Weapon);

        SET_MAP.put("CORE", Card.Set.Basic);
        SET_MAP.put("EXPERT1", Card.Set.Classic);
        SET_MAP.put("NAXX", Card.Set.Naxxramas);
        SET_MAP.put("PROMO", Card.Set.Promotion);
        SET_MAP.put("BRM", Card.Set.BlackrockMountain);
        SET_MAP.put("TGT", Card.Set.TheGrandTournament);
        SET_MAP.put("LOE", Card.Set.LeagueOfExplorers);
        SET_MAP.put("OG", Card.Set.WhisperOfTheOldGods);
        SET_MAP.put("MISSIONS", Card.Set.Missions);
        SET_MAP.put("CREDITS", Card.Set.Credits);
        SET_MAP.put("REWARD", Card.Set.Reward);

        RACE_MAP.put("BEAST", Card.Race.Beast);
        RACE_MAP.put("DRAGON", Card.Race.Dragon);
        RACE_MAP.put("MURLOC", Card.Race.Murloc);
        RACE_MAP.put("MECHANICAL", Card.Race.Mech);
        RACE_MAP.put("DEMON", Card.Race.Demon);
        RACE_MAP.put("TOTEM", Card.Race.Totem);
        RACE_MAP.put("PIRATE", Card.Race.Pirate);
        RACE_MAP.put(null, Card.Race.None);

        CLASS_MAP.put("NEUTRAL", HeroClass.Neutral);
        CLASS_MAP.put("PALADIN", HeroClass.Paladin);
        CLASS_MAP.put("WARRIOR", HeroClass.Warlock);
        CLASS_MAP.put("HUNTER", HeroClass.Hunter);
        CLASS_MAP.put("SHAMAN", HeroClass.Shaman);
        CLASS_MAP.put("ROGUE", HeroClass.Rogue);
        CLASS_MAP.put("DRUID", HeroClass.Druid);
        CLASS_MAP.put("MAGE", HeroClass.Mage);
        CLASS_MAP.put("WARLOCK", HeroClass.Warlock);
        CLASS_MAP.put("PRIEST", HeroClass.Priest);
    }

    public static void main(String[] args) throws IOException {

        for (int patch : patches) {
            LOG.info("Begin to crawl for patch {}.", patch);
            List<Card> cards = new ArrayList<>();

            String hearthJsonUrlStr = String.format(HEARTH_JSON_URL, patch);
            URL hearthJsonUrl = new URL(hearthJsonUrlStr);
            StringBuilder builder = new StringBuilder();
            LOG.info("Downloading from `{}` ...", hearthJsonUrlStr);
            URLConnection conn = hearthJsonUrl.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36");

            try (Scanner scanner = new Scanner(conn.getInputStream())) {
                while (scanner.hasNextLine()) {
                    builder.append(scanner.nextLine());
                    builder.append('\n');
                }
            }
            String response = builder.toString();

            LOG.info("Parsing response string as JSON ...");
            JSONArray jsonArr = new JSONArray(response);

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject cardJson = jsonArr.getJSONObject(i);
                if (cardJson.getString("type").equals("ENCHANTMENT"))
                    continue;
                if (cardJson.getString("set").equals("CHEAT"))
                    continue;
                Card card = new Card();

                LOG.info("Parsing card {} ...", cardJson.getJSONObject("name").getString("enUS"));

                // TODO Set id

                try {
                    LocaleString name = new LocaleString();
                    initLocaleString(name, cardJson.getJSONObject("name"));
                    card.setName(name);

                    if (cardJson.has("cost"))
                        card.setCost(cardJson.getInt("cost"));
                    if (cardJson.has("attack"))
                        card.setAttack(cardJson.getInt("attack"));
                    if (cardJson.has("health"))
                        card.setHealth(cardJson.getInt("health"));
                    else if (cardJson.has("durability"))
                        card.setHealth(cardJson.getInt("durability"));

                    if (cardJson.has("text")) {
                        LocaleString effect = new LocaleString();
                        initLocaleString(effect, cardJson.getJSONObject("text"));
                        card.setEffect(effect);
                    }

                    if (cardJson.has("flavor")) {
                        LocaleString desc = new LocaleString();
                        initLocaleString(desc, cardJson.getJSONObject("flavor"));
                        card.setDesc(desc);
                    }

                    if (cardJson.has("collectible") && cardJson.getBoolean("collectible"))
                        card.setCollectible(true);

                    if (cardJson.has("rarity"))
                        card.setQuality(QUALITY_MAP.get(cardJson.getString("rarity")));
                    else
                        card.setQuality(Card.Quality.Free);

                    card.setType(TYPE_MAP.get(cardJson.getString("type")));
                    if (card.getType() == Card.Type.Minion) {
                        if (cardJson.has("race"))
                            card.setRace(RACE_MAP.get(cardJson.getString("race")));
                        else
                            card.setRace(Card.Race.None);
                    }
                    card.setSet(SET_MAP.get(cardJson.getString("set")));
                    if (cardJson.has("playerClass"))
                        card.setHeroClass(CLASS_MAP.get(cardJson.getString("playerClass")));
                    else
                        card.setHeroClass(HeroClass.Neutral);

                    card.setImageUrl(cardJson.getString("id"));

                    LOG.info("Get card {}.", card.getName());
                    cards.add(card);
                } catch (Throwable ex) {
                    LOG.warn("Catch error: ", ex);
                }
            }

            LOG.info("Get {} cards. Launching GUI...", cards.size());
            CardCrawlerView.launch(cards, patch);
        }

    }

    private static void initLocaleString(LocaleString target, JSONObject source) {
        for (String key : LOCALE_MAP.keySet()) {
            target.put(LOCALE_MAP.get(key), source.getString(key));
        }
    }

}
