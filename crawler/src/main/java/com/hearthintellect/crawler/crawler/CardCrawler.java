package com.hearthintellect.crawler.crawler;
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
import java.util.*;

public class CardCrawler {
    private static final Logger LOG = LoggerFactory.getLogger(CardCrawler.class);

    public static Locale DEFAULT_LOCALE = new Locale("en", "US");
    private static final String HJ_CARD_JSON_URL = "D:\\GithubRepository\\HearthIntellect\\resources\\v1\\13807\\all\\cards.json";
    private static final String HH_CARD_JSON_URL = "D:\\GithubRepository\\HearthIntellect\\resources\\hearthheadCards.json";
    private static final String MECHANIC_JSON_URL = "D:\\GithubRepository\\HearthIntellect\\resources\\hearthheadMechanics.json";

    private static final int[] patches = { 3140, 3388, 3604, 3645, 3664, 3749, 3890, 3937, 4217, 4217, 4243, 4442,
        4458, 4482, 4944, 4973, 5170, 5314, 5435, 5506, 5834, 6024, 6141, 6284, 6485, 6898, 7234, 7628, 7785, 7835,
        8036, 8108, 8311, 8416, 8834, 9166, 9554, 9786, 10357, 10604, 10784, 10833, 10956, 11461, 11959, 12051,
        12105, 12266, 12574, 13030, 13619, 13714, 13740, 13807 };

    public static void main(String[] args) throws IOException {
        LOG.info("Parsing cards from Hearthstone JSON...");
        List<Card> hjCards = HearthJsonCardParser.parse(HJ_CARD_JSON_URL);
        LOG.info("Fetched {} cards.", hjCards.size());

        LOG.info("Parsing cards from HearthHead...");
        List<Card> hhCards = HearthHeadCardParser.parse(HH_CARD_JSON_URL);
        LOG.info("Fetched {} cards.", hhCards.size());

        LOG.info("Combining...");
        List<Card> cards = new ArrayList<>(hhCards.size());

        cards.addAll(hjCards);
        cards.sort((c1, c2) -> c1.getImageUrl().compareTo(c2.getImageUrl()));

        for (Card card : hhCards) {
            Card cardInHJ = CollectionUtils.binarySearch(cards, card.getImageUrl(), Card::getImageUrl);
            if (cardInHJ == null)
                cards.add(card);
            else
                cardInHJ.setHHID(card.getHHID());
        }

        LOG.info("Populated to {} cards.", cards.size());

        LOG.info("Parsing mechanics...");
        List<Mechanic> mechanics = HearthHeadCardParser.parseMechanics(MECHANIC_JSON_URL);

        // Link Card and Mechanic via crawling
        HearthHeadCardCrawler.crawl(cards, mechanics);

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
