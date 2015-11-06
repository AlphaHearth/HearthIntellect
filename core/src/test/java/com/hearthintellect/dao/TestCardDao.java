package com.hearthintellect.dao;

import com.hearthintellect.config.SpringMongoConfig;
import com.hearthintellect.model.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Vector;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for {@link CardRepository}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMongoConfig.class)
public class TestCardDao {
    private static final int TEST_ID = 100000;

    @Autowired
    private CardRepository cardRepository;

    @Test
    public void testCardDaoInsert() {
        Card card = new Card();

        card.setCardId(TEST_ID);
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
        card = cardRepository.findById(TEST_ID);

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

    @Test
    public void testCardDaoUpdate() {
        Card card = new Card();

        card.setCardId(TEST_ID);
        card.setName("Robert");
        card.setEffect("Deathrattle: close the website");
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

        card.setName("Mr-Dai");
        cardRepository.update(card);
        card = cardRepository.findById(TEST_ID);

        assertEquals(1, card.getCardId());
        assertEquals("Mr-Dai", card.getName());
    }

    @Test
    public void testCardDaoRemove() {
        Card card = new Card();
        card.setCardId(TEST_ID);

        cardRepository.remove(card);

        card = cardRepository.findById(TEST_ID);
        assertNull(card);
    }

    @Test
    @Ignore("The test depends on real production data. Failing on development environment is acceptable.")
    public void testCardDaoOnRealData() {
        // http://www.hearthhead.com/card=32/alakir-the-windlord
        Card card = cardRepository.findByHHID(32);

        assertThat(card.getName(), is("Al'Akir the Windlord"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.Classic));
        assertThat(card.getHeroClass(), is(HeroClass.Shaman));
        assertThat(card.getQuality(), is(Card.Quality.Legendary));

        // http://www.hearthhead.com/card=447/arcane-explosion
        card = cardRepository.findByHHID(447);

        assertThat(card.getName(), is("Arcane Explosion"));
        assertThat(card.getType(), is(Card.Type.Spell));
        assertThat(card.getSet(), is(Card.Set.Basic));
        assertThat(card.getHeroClass(), is(HeroClass.Mage));
        assertThat(card.getQuality(), is(Card.Quality.Free));

        // http://www.hearthhead.com/card=1805/deaths-bite
        card = cardRepository.findByHHID(1805);

        assertThat(card.getName(), is("Death's Bite"));
        assertThat(card.getType(), is(Card.Type.Weapon));
        assertThat(card.getSet(), is(Card.Set.Naxxramas));
        assertThat(card.getHeroClass(), is(HeroClass.Warrior));
        assertThat(card.getQuality(), is(Card.Quality.Common));

        // http://www.hearthhead.com/card=2826/alleria-windrunner
        card = cardRepository.findByHHID(2826);

        assertThat(card.getName(), is("Alleria Windrunner"));
        assertThat(card.getType(), is(Card.Type.Hero));
        assertThat(card.getHeroClass(), is(HeroClass.Hunter));
        assertThat(card.getQuality(), is(Card.Quality.Epic));

        // http://www.hearthhead.com/card=1123/shapeshift
        card = cardRepository.findByHHID(1123);

        assertThat(card.getName(), is("Shapeshift"));
        assertThat(card.getType(), is(Card.Type.HeroPower));
        assertThat(card.getHeroClass(), is(HeroClass.Druid));
        assertThat(card.getQuality(), is(Card.Quality.Free));

        // http://www.hearthhead.com/card=1997/fel-cannon
        card = cardRepository.findByHHID(1997);

        assertThat(card.getName(), is("Fel Cannon"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.GoblinsVsGnomes));
        assertThat(card.getRace(), is(Card.Race.Mech));
        assertThat(card.getHeroClass(), is(HeroClass.Warlock));
        assertThat(card.getQuality(), is(Card.Quality.Rare));

        // http://www.hearthhead.com/card=2655/murloc-knight
        card = cardRepository.findByHHID(2655);

        assertThat(card.getName(), is("Murloc Knight"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.TheGrandTournament));
        assertThat(card.getRace(), is(Card.Race.Murloc));
        assertThat(card.getHeroClass(), is(HeroClass.Paladin));
        assertThat(card.getQuality(), is(Card.Quality.Common));

        // http://www.hearthhead.com/card=1990/one-eyed-cheat
        card = cardRepository.findByHHID(1990);

        assertThat(card.getName(), is("One-eyed Cheat"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.GoblinsVsGnomes));
        assertThat(card.getRace(), is(Card.Race.Pirate));
        assertThat(card.getHeroClass(), is(HeroClass.Rogue));
        assertThat(card.getQuality(), is(Card.Quality.Rare));

        // http://www.hearthhead.com/card=2286/twilight-whelp
        card = cardRepository.findByHHID(2286);

        assertThat(card.getName(), is("Twilight Whelp"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.BlackrockMountain));
        assertThat(card.getRace(), is(Card.Race.Dragon));
        assertThat(card.getHeroClass(), is(HeroClass.Priest));
        assertThat(card.getQuality(), is(Card.Quality.Common));

        // http://www.hearthhead.com/card=858/gelbin-mekkatorque
        card = cardRepository.findByHHID(858);

        assertThat(card.getName(), is("Gelbin Mekkatorque"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.Promotion));
        assertThat(card.getRace(), is(Card.Race.None));
        assertThat(card.getHeroClass(), is(HeroClass.Neutral));
        assertThat(card.getQuality(), is(Card.Quality.Legendary));

        // http://www.hearthhead.com/card=530/captains-parrot
        card = cardRepository.findByHHID(530);

        assertThat(card.getName(), is("Captain's Parrot"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.Reward));
        assertThat(card.getRace(), is(Card.Race.Beast));
        assertThat(card.getHeroClass(), is(HeroClass.Neutral));
        assertThat(card.getQuality(), is(Card.Quality.Epic));

        // http://www.hearthhead.com/card=1986/malganis
        card = cardRepository.findByHHID(1986);

        assertThat(card.getName(), is("Mal'Ganis"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.GoblinsVsGnomes));
        assertThat(card.getRace(), is(Card.Race.Demon));
        assertThat(card.getHeroClass(), is(HeroClass.Warlock));
        assertThat(card.getQuality(), is(Card.Quality.Legendary));

        // http://www.hearthhead.com/card=2007/vitality-totem
        card = cardRepository.findByHHID(2007);

        assertThat(card.getName(), is("Vitality Totem"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.GoblinsVsGnomes));
        assertThat(card.getRace(), is(Card.Race.Totem));
        assertThat(card.getHeroClass(), is(HeroClass.Shaman));
        assertThat(card.getQuality(), is(Card.Quality.Rare));

    }

}
