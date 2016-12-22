package com.hearthintellect.crawler;

import com.hearthintellect.config.SpringCoreConfig;
import com.hearthintellect.dao.CardRepository;
import com.hearthintellect.dao.PatchRepository;
import com.hearthintellect.model.Card;
import com.hearthintellect.model.HeroClass;
import com.hearthintellect.model.HistoryCard;
import com.hearthintellect.model.Patch;
import com.hearthintellect.utils.CollectionUtils;
import com.hearthintellect.utils.ConcurrentUtils;
import com.hearthintellect.utils.LocaleString;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.hearthintellect.crawler.Constants.DEFAULT_LOCALE;

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
        QUALITY_MAP.put("UNKNOWN_6", Card.Quality.Free);

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
        SET_MAP.put("KARA", Card.Set.OneNightInKarazhan);
        SET_MAP.put("KARA_RESERVE", Card.Set.OneNightInKarazhan);
        SET_MAP.put("GANGS", Card.Set.MeanStreetsOfGadgetzan);

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

    public static void main(String[] args) throws InterruptedException {
        LOG.info("Parsing cards from Hearthstone JSON...");
        Map<Integer, List<Card>> versionCards = new ConcurrentHashMap<>();

        ExecutorService executor = Executors.newFixedThreadPool(30);

        Map<Integer, Patch> patchEntities = new HashMap<>();
        for (int patchNum : Constants.PATCHES) {
            patchEntities.put(patchNum, new Patch(patchNum, ""));
            executor.submit(() -> {
                List<Card> cards;
                try {
                    cards = parse(Constants.HEARTHSTONE_JSON_PATH.resolve(String.valueOf(patchNum) + ".json"));
                } catch (Throwable ex) {
                    LOG.error("Caught exception: ", ex);
                    return;
                }
                LOG.info("Fetched {} cards for version {}.", cards.size(), patchNum);
                cards.sort((c1, c2) -> c1.getId().compareTo(c2.getId()));

                versionCards.put(patchNum, cards);
            });
        }
        ConcurrentUtils.shutdownAndWait(executor);

        // Compare same card of different versions to generate card changes
        int latestVersion = Constants.PATCHES[Constants.PATCHES.length - 1];
        List<Card> cards = new ArrayList<>(versionCards.get(latestVersion));
        LOG.info("Combining cards from different version...");
        for (Card card : cards) {
            LOG.debug("Scanning card `{}`", card.getName().get(DEFAULT_LOCALE));
            HistoryCard earliestKnownVersion = new HistoryCard(card);
            int currentPatchNum = latestVersion;
            Patch currentPatch = patchEntities.get(currentPatchNum);
            for (int i = Constants.PATCHES.length - 2; i >= 0; i--) {
                List<Card> oldCards = versionCards.get(Constants.PATCHES[i]);
                Card oldCard = CollectionUtils.binarySearch(oldCards, card.getImageUrl(), Card::getImageUrl);
                if (oldCard == null) {
                    LOG.debug("Cannot find card `{}` in version `{}`", card.getName().get(DEFAULT_LOCALE), currentPatchNum);
                    if (card.getSincePatch() != null) {
                        earliestKnownVersion.setSincePatch(currentPatch);
                        if (card.getHistoryVersions().isEmpty())
                            card.setHistoryVersions(new LinkedList<>());
                        card.getHistoryVersions().add(0, earliestKnownVersion);
                    } else
                        card.setSincePatch(currentPatch);
                    card.setAddedPatch(currentPatch);
                    break;
                }

                if (!earliestKnownVersion.getEffect().isEmpty()
                        && !earliestKnownVersion.getEffect().get(DEFAULT_LOCALE).equals(oldCard.getEffect().get(DEFAULT_LOCALE))
                        || earliestKnownVersion.getCost() != oldCard.getCost()
                        || earliestKnownVersion.getAttack() != oldCard.getAttack()
                        || earliestKnownVersion.getHealth() != oldCard.getHealth()) {
                    LOG.debug("Detected change of card `{}` in patch `{}`.", card.getName().get(DEFAULT_LOCALE),
                            currentPatchNum);
                    if (card.getSincePatch() == null) {
                        card.setSincePatch(currentPatch);
                    } else {
                        earliestKnownVersion.setSincePatch(currentPatch);
                        if (card.getHistoryVersions().isEmpty())
                            card.setHistoryVersions(new LinkedList<>());
                        card.getHistoryVersions().add(0, earliestKnownVersion);
                    }
                    earliestKnownVersion = new HistoryCard(oldCard);
                }

                currentPatchNum = Constants.PATCHES[i];
                currentPatch = patchEntities.get(currentPatchNum);
            }
        }

        LOG.info("Initializing link to database...");
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringCoreConfig.class);

        LOG.info("Saving Patches to database...");
        PatchRepository patchRepository = context.getBean(PatchRepository.class);
        patchEntities.values().forEach(patchRepository::insert);

        LOG.info("Saving Cards to database...");
        CardRepository cardRepository = context.getBean(CardRepository.class);
        cards.forEach(cardRepository::insert);
    }

    public static List<Card> parse(Path jsonFilePath) throws MalformedURLException {
        LOG.info("Reading card JSON from `{}`", jsonFilePath.toAbsolutePath().toString());

        String json = null;
        try {
            json = FileUtils.readFileToString(jsonFilePath.toFile(), "UTF-8");
        } catch (IOException ex) {
            LOG.error("Failed to read Json file `" + jsonFilePath.toAbsolutePath() + "`: ", ex);
            System.exit(1); // TODO Think twice
        }

        LOG.info("Parsing card JSON...");
        JSONArray jsonCards = new JSONArray(json);

        List<Card> cards = new ArrayList<>(jsonCards.length());

        for (int i = 0; i < jsonCards.length(); i++) {
            JSONObject cardJson = jsonCards.getJSONObject(i);
            if (cardJson.has("type") && cardJson.getString("type").equals("ENCHANTMENT"))
                continue;
            if (cardJson.has("set") &&
                    (cardJson.getString("set").equals("CHEAT") || cardJson.getString("set").equals("NONE")))
                continue;

            Card card = new Card();

            try {
                LOG.debug("Parsing card {} ...", cardJson.getJSONObject("name").getString("enUS"));
            } catch (JSONException ex) {
                LOG.error("Failed to locate `name` field for json object `{}`.", cardJson.toString());
                continue;
            }

            try {
                LocaleString name = new LocaleString();
                parseLocale(name, cardJson.getJSONObject("name"));
                card.setName(name);

                card.setId(cardJson.getString("id"));
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
                card.setFlavor(description);

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
                LOG.warn("Caught error: ", ex);
            }
        }
        LOG.info("Returning fetched cards of `{}`.", jsonFilePath.toAbsolutePath().toString());
        return cards;
    }

    private static void parseLocale(LocaleString localeStr, JSONObject json) {
        for (Locale locale : LOCALE_MAP.keySet()) {
            try {
                localeStr.put(locale, json.getString(LOCALE_MAP.get(locale)));
            } catch (JSONException ex) {
                LOG.error("Failed to locate `{}` locale field for JsonObject `{}`. Filling with empty value...",
                        LOCALE_MAP.get(locale), json.toString());
                localeStr.put(locale, "");
            }
        }
    }
}
