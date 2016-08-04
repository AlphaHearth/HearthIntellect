package com.hearthintellect.crawler.crawler;

import com.hearthintellect.model.Card;
import com.hearthintellect.model.HeroClass;
import com.hearthintellect.util.LocaleString;
import com.hearthintellect.utils.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class HearthJsonCardParser {
    private static final Logger LOG = LoggerFactory.getLogger(HearthJsonCardParser.class);

    private static final Map<Locale, String> LOCALE_MAP = new HashMap<>();
    private static final Map<String, Card.Quality> QUALITY_MAP = new HashMap<>();
    private static final Map<String, Card.Type> TYPE_MAP = new HashMap<>();
    private static final Map<String, Card.Set> SET_MAP = new HashMap<>();
    private static final Map<String, Card.Race> RACE_MAP = new HashMap<>();
    private static final Map<String, HeroClass> CLASS_MAP = new HashMap<>();

    static {
        LOCALE_MAP.put(new Locale("en", "US"), "enUS");
        LOCALE_MAP.put(new Locale("de", "DE"), "deDE");
        LOCALE_MAP.put(new Locale("es", "ES"), "esES");
        LOCALE_MAP.put(new Locale("es", "MX"), "esMX");
        LOCALE_MAP.put(new Locale("fr", "FR"), "frFR");
        LOCALE_MAP.put(new Locale("it", "IT"), "itIT");
        LOCALE_MAP.put(new Locale("ja", "JP"), "jaJP");
        LOCALE_MAP.put(new Locale("ko", "KR"), "koKR");
        LOCALE_MAP.put(new Locale("pl", "PL"), "plPL");
        LOCALE_MAP.put(new Locale("pt", "BR"), "ptBR");
        LOCALE_MAP.put(new Locale("ru", "RU"), "ruRU");
        LOCALE_MAP.put(new Locale("th", "TH"), "thTH");
        LOCALE_MAP.put(new Locale("zh", "CN"), "zhCN");
        LOCALE_MAP.put(new Locale("zh", "TW"), "zhTW");

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
        SET_MAP.put("REWARD", Card.Set.Reward);
        SET_MAP.put("MISSIONS", Card.Set.Missions);
        SET_MAP.put("PROMO", Card.Set.Promotion);
        SET_MAP.put("NAXX", Card.Set.Naxxramas);
        SET_MAP.put("GVG", Card.Set.GoblinsVsGnomes);
        SET_MAP.put("BRM", Card.Set.BlackrockMountain);
        SET_MAP.put("TGT", Card.Set.TheGrandTournament);
        SET_MAP.put("HERO_SKINS", Card.Set.AlternativeHeros);
        SET_MAP.put("CREDITS", Card.Set.Credits);
        SET_MAP.put("LOE", Card.Set.LeagueOfExplorers);
        SET_MAP.put("TB", Card.Set.TavernBrawl);
        SET_MAP.put("OG", Card.Set.WhisperOfTheOldGods);

        RACE_MAP.put("BEAST", Card.Race.Beast);
        RACE_MAP.put("DRAGON", Card.Race.Dragon);
        RACE_MAP.put("MURLOC", Card.Race.Murloc);
        RACE_MAP.put("MECHANICAL", Card.Race.Mech);
        RACE_MAP.put("DEMON", Card.Race.Demon);
        RACE_MAP.put("TOTEM", Card.Race.Totem);
        RACE_MAP.put("PIRATE", Card.Race.Pirate);

        CLASS_MAP.put("DREAM", HeroClass.Dream);
        CLASS_MAP.put("PALADIN", HeroClass.Paladin);
        CLASS_MAP.put("WARLOCK", HeroClass.Warlock);
        CLASS_MAP.put("HUNTER", HeroClass.Hunter);
        CLASS_MAP.put("SHAMAN", HeroClass.Shaman);
        CLASS_MAP.put("ROGUE", HeroClass.Rogue);
        CLASS_MAP.put("DRUID", HeroClass.Druid);
        CLASS_MAP.put("MAGE", HeroClass.Mage);
        CLASS_MAP.put("WARRIOR", HeroClass.Warrior);
        CLASS_MAP.put("PRIEST", HeroClass.Priest);
        CLASS_MAP.put("NEUTRAL", HeroClass.Neutral);
    }

    public static List<Card> parse(String jsonFileUrl) {
        LOG.info("Reading card JSON from `{}`", jsonFileUrl);
        String json = null;
        try {
            json = IOUtils.readFile(jsonFileUrl);
        } catch (IOException ex) {
            LOG.error("Failed to read `" + jsonFileUrl + "`", ex);
            return Collections.emptyList();
        }
        JSONArray jsonCards = new JSONArray(json);

        LOG.info("Parsing card JSON...");

        List<Card> cards = new ArrayList<>(jsonCards.length());

        for (int i = 0; i < jsonCards.length(); i++) {
            JSONObject cardJson = jsonCards.getJSONObject(i);
            if (cardJson.has("type") && cardJson.getString("type").equals("ENCHANTMENT"))
                continue;
            if (cardJson.has("set") &&
                    (cardJson.getString("set").equals("CHEAT") || cardJson.getString("set").equals("NONE")))
                continue;

            Card card = new Card();

            LOG.debug("Parsing card {} ...", cardJson.getJSONObject("name").getString("enUS"));

            try {
                LocaleString name = new LocaleString();
                parseLocale(name, cardJson.getJSONObject("name"));
                card.setName(name);

                card.setImageUrl(cardJson.getString("id"));

                if (cardJson.has("cost"))
                    card.setCost(cardJson.getInt("cost"));
                if (cardJson.has("attack"))
                    card.setAttack(cardJson.getInt("attack"));
                if (cardJson.has("health"))
                    card.setHealth(cardJson.getInt("health"));
                else if (cardJson.has("durability"))
                    card.setHealth(cardJson.getInt("durability"));

                LocaleString effect = new LocaleString();
                if (cardJson.has("text"))
                    parseLocale(effect, cardJson.getJSONObject("text"));
                card.setEffect(effect);

                LocaleString description = new LocaleString();
                if (cardJson.has("flavor"))
                    parseLocale(description, cardJson.getJSONObject("flavor"));
                card.setDesc(description);

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

                if (cardJson.has("set"))
                    card.setSet(SET_MAP.get(cardJson.getString("set")));

                if (cardJson.has("playerClass"))
                    card.setHeroClass(CLASS_MAP.get(cardJson.getString("playerClass")));
                else
                    card.setHeroClass(HeroClass.Neutral);

                // Validate
                if (card.getSet() == null)
                    LOG.error("Failed to set Card.Set for card {}.", cardJson.toString());
                if (card.getType() == null)
                    LOG.error("Failed to set Card.Type for card {}.", cardJson.toString());
                if (card.getQuality() == null)
                    LOG.error("Failed to set Card.Quality for card {}.", cardJson.toString());
                if (card.getType() == Card.Type.Minion && card.getRace() == null)
                    LOG.error("Failed to set Card.Race for card {}.", cardJson.toString());
                if (card.getHeroClass() == null)
                    LOG.error("Failed to set HeroClass for card {}.", cardJson.toString());

                cards.add(card);
            } catch (Throwable ex) {
                LOG.warn("Catch error: ", ex);
            }
        }
        return cards;
    }

    private static void parseLocale(LocaleString localeStr, JSONObject json) {
        for (Locale locale : LOCALE_MAP.keySet())
            localeStr.put(locale, json.getString(LOCALE_MAP.get(locale)));
    }
}
