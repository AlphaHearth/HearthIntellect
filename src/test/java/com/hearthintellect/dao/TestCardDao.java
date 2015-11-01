package com.hearthintellect.dao;

import com.hearthintellect.model.*;
import com.hearthintellect.model.Class;
import org.junit.Test;

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

        card.setQuality(CardQuality.Legendary);
        card.setRace(Race.None);
        card.setSet(CardSet.Credits);
        card.setType(CardType.Minion);
        card.set_class(Class.Neutral);

        cardRepository.save(card);
        card = cardRepository.findById(1);

        assertEquals(1, card.getCardId());
        assertEquals("Robert", card.getName());
        assertEquals("Battlecry: publish a website", card.getEffect());
        assertEquals("The Author of the website", card.getDesc());
        assertEquals(2, card.getCost());
        assertEquals(2, card.getAttack());
        assertEquals(3, card.getHealth());
        assertEquals(CardQuality.Legendary, card.getQuality());
        assertEquals(Race.None, card.getRace());
        assertEquals(CardSet.Credits, card.getSet());
        assertEquals(CardType.Minion, card.getType());
        assertEquals(Class.Neutral, card.get_class());

    }

}
