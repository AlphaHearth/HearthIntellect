package com.hearthintellect.crawler;
import com.hearthintellect.config.SpringCoreConfig;
import com.hearthintellect.dao.CardRepository;
import com.hearthintellect.dao.MechanicRepository;
import com.hearthintellect.model.Card;
import com.hearthintellect.model.Mechanic;
import com.hearthintellect.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CardCrawler {
    private static final Logger LOG = LoggerFactory.getLogger(CardCrawler.class);

    static final Locale DEFAULT_LOCALE = new Locale("en", "US");

    private static final String HJ_CARD_JSON_URL = "https://api.hearthstonejson.com/v1/13921/all/cards.json";
    private static final Path HH_CARD_JSON_PATH = Paths.get("resources", "hearthheadCards.json");
    private static final Path MECHANIC_JSON_PATH = Paths.get("resources", "hearthheadMechanics.json");

    public static void main(String[] args) throws IOException {
        LOG.info("Parsing cards from Hearthstone JSON...");
        List<Card> cards = HearthJsonCardParser.parse(HJ_CARD_JSON_URL);
        LOG.info("Fetched {} cards.", cards.size());

        cards.sort((c1, c2) -> c1.getImageUrl().compareTo(c2.getImageUrl()));

        LOG.info("Parsing mechanics...");
        List<Mechanic> mechanics = HearthHeadCardParser.parseMechanics(MECHANIC_JSON_PATH);

        // Link Card and Mechanic via crawling
        HearthHeadCardCrawler.crawl(cards, mechanics);

        // Sort and assign ID to each card
        cards.sort((c1, c2) -> {
            if (c1.getSet() != c2.getSet())
                return c1.getSet().compareTo(c2.getSet());
            if (c1.getHeroClass() != c2.getHeroClass())
                return c1.getHeroClass().compareTo(c2.getHeroClass());
            if (c1.getCost() != c2.getCost())
                return Integer.compare(c1.getCost(), c2.getCost());
            return c1.getName().get(DEFAULT_LOCALE).compareTo(c2.getName().get(DEFAULT_LOCALE));
        });
        for (int i = 0; i < cards.size(); i++)
            cards.get(i).setId(i + 1);

        // Sort mechanics
        mechanics.sort((m1, m2) -> Integer.compare(m1.getId(), m2.getId()));

        LOG.info("Initializing links to database...");
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringCoreConfig.class);

        LOG.info("Saving Mechanics to database...");
        MechanicRepository mechanicRepository = context.getBean(MechanicRepository.class);
        mechanics.forEach(mechanicRepository::insert);

        LOG.info("Saving Cards to database...");
        CardRepository cardRepository = context.getBean(CardRepository.class);
        cards.forEach(cardRepository::insert);

    }

}
