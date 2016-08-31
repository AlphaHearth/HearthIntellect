package com.hearthintellect.crawler.crawler;

import com.hearthintellect.model.Card;
import com.hearthintellect.model.HeroClass;
import com.hearthintellect.model.Mechanic;
import com.hearthintellect.util.LocaleString;
import com.hearthintellect.utils.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class HearthHeadCardParser {
    private static final Logger LOG = LoggerFactory.getLogger(HearthHeadCardParser.class);


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
        CLASS_MAP.put(1, HeroClass.Warrior);
        CLASS_MAP.put(3, HeroClass.Hunter);
        CLASS_MAP.put(7, HeroClass.Shaman);
        CLASS_MAP.put(4, HeroClass.Rogue);
        CLASS_MAP.put(11, HeroClass.Druid);
        CLASS_MAP.put(8, HeroClass.Mage);
        CLASS_MAP.put(9, HeroClass.Warlock);
        CLASS_MAP.put(5, HeroClass.Priest);
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

        Set<Integer> hhid = new HashSet<>();

        for (int i = 0; i < jsonCards.length(); i++) {
            JSONObject cardJson = jsonCards.getJSONObject(i);
            Card card = new Card();

            LOG.debug("Parsing card {} ...", cardJson.getString("name"));

            try {
                LocaleString name = new LocaleString();
                name.put(CardCrawler.DEFAULT_LOCALE, cardJson.getString("name"));
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
                    effect.put(CardCrawler.DEFAULT_LOCALE, cardJson.getString("description"));
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

    public static List<Mechanic> parseMechanics(String jsonFileUrl) {
        String json = null;
        try {
            json = IOUtils.readFile(jsonFileUrl);
        } catch (IOException ex) {
            LOG.error("Failed to read `" + jsonFileUrl + "`", ex);
            return Collections.emptyList();
        }
        JSONArray jsonMechanics = new JSONArray(json);

        List<Mechanic> mechanics = new ArrayList<>(jsonMechanics.length());

        for (int i = 0; i < jsonMechanics.length(); i++) {
            JSONObject jsonMechanic = jsonMechanics.getJSONObject(i);
            Mechanic mechanic = new Mechanic();
            mechanic.setId(jsonMechanic.getInt("_id"));
            mechanic.setHHID(jsonMechanic.getInt("HHID"));
            LocaleString name = new LocaleString();
            mechanic.setName(name);
            name.put(CardCrawler.DEFAULT_LOCALE, jsonMechanic.getString("name"));
            LocaleString description = new LocaleString();
            description.put(CardCrawler.DEFAULT_LOCALE, jsonMechanic.getString("description"));
            mechanic.setDescription(description);
            mechanics.add(mechanic);
        }

        return mechanics;
    }

}

