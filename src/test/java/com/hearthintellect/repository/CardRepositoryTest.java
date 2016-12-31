package com.hearthintellect.repository;

import com.hearthintellect.config.InMemoryMongoConfig;
import com.hearthintellect.model.Card;
import com.hearthintellect.model.CardQuote;
import com.hearthintellect.model.HeroClass;
import com.hearthintellect.utils.LocaleString;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Basic CURD unit tests for {@link CardRepository}, strictly-written in a manner that does not rely on any
 * specific database access technique but the interface declaration itself.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemoryMongoConfig.class)
public class CardRepositoryTest {
    private static final String TEST_ID = "ROBERT_0762";

    @Autowired private CardRepository cardRepository;

    private Card testCard;

    @Before
    public void setUp() {
        testCard = new Card();

        testCard.setCardId(TEST_ID);
        LocaleString cardName = new LocaleString();
        cardName.put(Locale.US, "Robert");
        cardName.put(Locale.CHINA, "呆呆");
        testCard.setName(cardName);
        LocaleString cardEffect = new LocaleString();
        cardEffect.put(Locale.US, "Battlecry: publish a website");
        cardEffect.put(Locale.CHINA, "战吼：发布一个网站");
        testCard.setEffect(cardEffect);
        LocaleString cardDesc = new LocaleString();
        cardDesc.put(Locale.US, "The Author of the website");
        cardDesc.put(Locale.CHINA, "网站作者");
        testCard.setFlavor(cardDesc);

        testCard.setCost(2);
        testCard.setAttack(3);
        testCard.setHealth(3);

        testCard.setQuality(Card.Quality.LEGENDARY);
        testCard.setRace(Card.Race.NONE);
        testCard.setSet(Card.Set.CREDITS);
        testCard.setType(Card.Type.MINION);
        testCard.setHeroClass(HeroClass.NEUTRAL);

        List<CardQuote> quotes = new ArrayList<>();
        LocaleString playQuote = new LocaleString();
        playQuote.put(Locale.US, "Fear me, if you dare!");
        quotes.add(new CardQuote(CardQuote.Type.Play, playQuote, "url1"));
        LocaleString attackQuote = new LocaleString();
        attackQuote.put(Locale.US, "Ahahaha, don't make me laugh!");
        quotes.add(new CardQuote(CardQuote.Type.Attack, attackQuote, "url2"));
        LocaleString deathQuote = new LocaleString();
        deathQuote.put(Locale.US, "This is just the beginning!");
        quotes.add(new CardQuote(CardQuote.Type.Death, deathQuote, "url3"));
        testCard.setQuotes(quotes);
        cardRepository.insert(testCard);
    }

    @Test
    public void testCardDaoInsertAndRead() {
        // The entity was inserted in the `@Before` method
        Card card = cardRepository.findById(TEST_ID);

        assertThat(card.getCardId(), is(testCard.getCardId()));
        assertThat(card.getName(), is(testCard.getName()));
        assertThat(card.getEffect(), is(testCard.getEffect()));
        assertThat(card.getFlavor(), is(testCard.getFlavor()));
        assertThat(card.getCost(), is(testCard.getCost()));
        assertThat(card.getAttack(), is(testCard.getAttack()));
        assertThat(card.getHealth(), is(testCard.getHealth()));
        assertThat(card.getQuality(), is(testCard.getQuality()));
        assertThat(card.getRace(), is(testCard.getRace()));
        assertThat(card.getSet(), is(testCard.getSet()));
        assertThat(card.getType(), is(testCard.getType()));
        assertThat(card.getHeroClass(), is(testCard.getHeroClass()));
    }

    @Test
    public void testCardDaoUpdate() {
        Card card = cardRepository.findById(TEST_ID);

        LocaleString cardName = new LocaleString();
        cardName.put(Locale.US, "Mr-Dai");
        cardName.put(Locale.CHINA, "呆呆");
        card.setName(cardName);
        cardRepository.update(card);
        card = cardRepository.findById(TEST_ID);

        assertThat(card.getCardId(), is(testCard.getCardId()));
        assertThat(card.getName(), not(is(testCard.getName())));
        assertThat(card.getName(), is(cardName));
    }

    @Test
    public void testCardDaoRemove() {
        Card card = new Card();
        card.setCardId(TEST_ID);

        cardRepository.delete(card);

        card = cardRepository.findById(TEST_ID);
        assertThat(card, nullValue());
    }
}
