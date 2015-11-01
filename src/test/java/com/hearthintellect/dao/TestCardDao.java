package com.hearthintellect.dao;

import com.hearthintellect.model.*;
import com.hearthintellect.model.HeroClass;
import org.junit.Test;

import java.util.List;
import java.util.Vector;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link CardRepository}
 */
public class TestCardDao {

    private CardRepository cardRepository;

    @Test
    public void testCardDao() {
        Card card = new Card();

        card.setCardId(1);
        card.setName("Robert");
        card.setEffect("Battlecry: publish a website");
        card.setDesc("The Author of the website");

        card.setCost(2);
        card.setAttack(2);
        card.setHealth(3);

        card.setQuality(Card.Quality.Legendary);
        card.setRace(Card.Race.None);
        card.setSet(Card.Set.Credits);
        card.setType(Card.Type.Minion);
        card.setHeroClass(HeroClass.Neutral);

        List<CardQuote> quotes = new Vector<>();
        quotes.add(new CardQuote(CardQuote.Type.Play, "Fear me, if you dare!", "url1"));
        quotes.add(new CardQuote(CardQuote.Type.Attack, "Ahahaha, don't make me laugh!", "url2"));
        quotes.add(new CardQuote(CardQuote.Type.Death, "This is just the beginning!", "url3"));
        card.setQuotes(quotes);

        cardRepository.save(card);
        card = cardRepository.findById(1);

        assertEquals(1, card.getCardId());
        assertEquals("Robert", card.getName());
        assertEquals("Battlecry: publish a website", card.getEffect());
        assertEquals("The Author of the website", card.getDesc());
        assertEquals(2, card.getCost());
        assertEquals(2, card.getAttack());
        assertEquals(3, card.getHealth());
        assertEquals(Card.Quality.Legendary, card.getQuality());
        assertEquals(Card.Race.None, card.getRace());
        assertEquals(Card.Set.Credits, card.getSet());
        assertEquals(Card.Type.Minion, card.getType());
        assertEquals(HeroClass.Neutral, card.getHeroClass());

    }

}
