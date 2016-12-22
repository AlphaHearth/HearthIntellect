package com.hearthintellect.crawler;

import com.hearthintellect.model.Card;
import com.hearthintellect.model.HeroClass;
import com.hearthintellect.model.Mechanic;
import com.hearthintellect.utils.LocaleString;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public static List<Mechanic> parseMechanics(Path jsonPath) {
        String json = null;
        try {
            json = new String(Files.readAllBytes(jsonPath));
        } catch (IOException ex) {
            LOG.error("Failed to read `" + jsonPath + "`", ex);
            return Collections.emptyList();
        }
        JSONArray jsonMechanics = new JSONArray(json);

        List<Mechanic> mechanics = new ArrayList<>(jsonMechanics.length());

        for (int i = 0; i < jsonMechanics.length(); i++) {
            JSONObject jsonMechanic = jsonMechanics.getJSONObject(i);
            Mechanic mechanic = new Mechanic();
            mechanic.setId(jsonMechanic.getInt("_id"));
            LocaleString name = new LocaleString();
            mechanic.setName(name);
            name.put(Constants.DEFAULT_LOCALE, jsonMechanic.getString("name"));
            LocaleString description = new LocaleString();
            description.put(Constants.DEFAULT_LOCALE, jsonMechanic.getString("description"));
            mechanic.setDescription(description);
            mechanics.add(mechanic);
        }

        return mechanics;
    }

}

