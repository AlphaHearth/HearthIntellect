package com.hearthintellect.crawler;
import com.hearthintellect.config.SpringCoreConfig;
import com.hearthintellect.dao.CardRepository;
import com.hearthintellect.dao.MechanicRepository;
import com.hearthintellect.model.Card;
import com.hearthintellect.model.HistoryCard;
import com.hearthintellect.model.Mechanic;
import com.hearthintellect.model.Patch;
import com.hearthintellect.utils.CollectionUtils;
import com.hearthintellect.utils.ConcurrentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CardCrawler {
    private static final Logger LOG = LoggerFactory.getLogger(CardCrawler.class);

    static final Locale DEFAULT_LOCALE = new Locale("en", "US");

    private static final String HJ_CARD_JSON_URL = "https://api.hearthstonejson.com/v1/%d/all/cards.json";
    private static final Path MECHANIC_JSON_PATH = Paths.get("resources", "hearthheadMechanics.json");

    public static void main(String[] args) throws IOException, InterruptedException {
        LOG.info("Parsing cards from Hearthstone JSON...");
        Map<Integer, List<Card>> versionCards = new ConcurrentHashMap<>();

        ExecutorService executor = Executors.newFixedThreadPool(30);

        for (int patchNum : Constants.PATCHES) {
            executor.submit(() -> {
                List<Card> cards;
                try {
                    cards = HearthJsonCardParser.parse(String.format(HJ_CARD_JSON_URL, patchNum));
                } catch (MalformedURLException ex) {
                    return;
                }
                LOG.info("Fetched {} cards for version {}.", cards.size(), patchNum);
                cards.sort((c1, c2) -> c1.getImageUrl().compareTo(c2.getImageUrl()));

                versionCards.put(patchNum, cards);
            });
        }
        ConcurrentUtils.shutdownAndWait(executor);

        int latestVersion = Constants.PATCHES[Constants.PATCHES.length - 1];
        List<Card> cards = new ArrayList<>(versionCards.get(latestVersion));
        LOG.info("Combining cards from different version...");
        for (Card card : cards) {
            LOG.debug("Scanning card `{}`", card.getName().get(DEFAULT_LOCALE));
            HistoryCard earliestKnownVersion = new HistoryCard(card);
            int currentPatchNum = latestVersion;
            Patch currentPatch = new Patch(currentPatchNum, "");
            for (int i = Constants.PATCHES.length - 2; i >= 0; i--) {
                List<Card> oldCards = versionCards.get(Constants.PATCHES[i]);
                Card oldCard = CollectionUtils.binarySearch(oldCards, card.getImageUrl(), Card::getImageUrl);
                if (oldCard == null) {
                    LOG.debug("Cannot find card `{}` in version `{}`",
                        card.getName().get(DEFAULT_LOCALE), currentPatchNum);
                    if (!card.getHistoryVersions().isEmpty()) {
                        earliestKnownVersion.setSincePatch(currentPatch);
                        card.getHistoryVersions().add(0, earliestKnownVersion);
                    } else
                        card.setSincePatch(currentPatch);
                    break;
                }

                if (!earliestKnownVersion.getEffect().isEmpty()
                    && !earliestKnownVersion.getEffect().get(DEFAULT_LOCALE).equals(oldCard.getEffect().get(DEFAULT_LOCALE))
                        || earliestKnownVersion.getCost() != oldCard.getCost()
                        || earliestKnownVersion.getAttack() != oldCard.getAttack()
                        || earliestKnownVersion.getHealth() != oldCard.getHealth()) {
                    LOG.debug("Detected change of card `{}` in patch `{}`.", card.getName().get(DEFAULT_LOCALE),
                        currentPatchNum);
                    earliestKnownVersion.setSincePatch(currentPatch);
                    if (card.getSincePatch() == null)
                        card.setSincePatch(currentPatch);
                    if (card.getHistoryVersions().isEmpty())
                        card.setHistoryVersions(new LinkedList<>());
                    card.getHistoryVersions().add(0, earliestKnownVersion);
                    earliestKnownVersion = new HistoryCard(oldCard);
                }

                currentPatchNum = Constants.PATCHES[i];
                currentPatch = new Patch(currentPatchNum, "");
            }
        }

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

        LOG.info("Initializing link to database...");
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringCoreConfig.class);

        LOG.info("Saving Mechanics to database...");
        MechanicRepository mechanicRepository = context.getBean(MechanicRepository.class);
        mechanics.forEach(mechanicRepository::insert);

        LOG.info("Saving Cards to database...");
        CardRepository cardRepository = context.getBean(CardRepository.class);
        cards.forEach(cardRepository::insert);

    }

}
