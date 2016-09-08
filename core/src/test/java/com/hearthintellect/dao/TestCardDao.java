package com.hearthintellect.dao;

import com.hearthintellect.config.SpringMongoConfig;
import com.hearthintellect.model.*;
import com.hearthintellect.utils.LocaleString;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

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
        LocaleString cardName = new LocaleString();
        cardName.put(Locale.US, "Robert");
        cardName.put(Locale.CHINA, "呆呆");
        card.setName(cardName);
        LocaleString cardEffect = new LocaleString();
        cardEffect.put(Locale.US, "Battlecry: publish a website");
        cardEffect.put(Locale.CHINA, "战吼：发布一个网站");
        card.setEffect(cardEffect);
        LocaleString cardDesc = new LocaleString();
        cardDesc.put(Locale.US, "The Author of the website");
        cardDesc.put(Locale.CHINA, "网站作者");
        card.setDesc(cardDesc);

        card.setCost(2);
        card.setAttack(2);
        card.setHealth(3);

        card.setQuality(Card.Quality.Legendary);
        card.setRace(Card.Race.None);
        card.setSet(Card.Set.Credits);
        card.setType(Card.Type.Minion);
        card.setHeroClass(HeroClass.Neutral);

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
        card.setQuotes(quotes);

        cardRepository.insert(card);
        card = cardRepository.findById(TEST_ID);

        assertEquals(TEST_ID, card.getCardId());
        assertEquals(cardName, card.getName());
        assertEquals(cardEffect, card.getEffect());
        assertEquals(cardDesc, card.getDesc());
        assertEquals(2, card.getCost());
        assertEquals(2, card.getAttack());
        assertEquals(3, card.getHealth());
        assertEquals(Card.Quality.Legendary, card.getQuality());
        assertEquals(Card.Race.None, card.getRace());
        assertEquals(Card.Set.Credits, card.getSet());
        assertEquals(Card.Type.Minion, card.getType());
        assertEquals(HeroClass.Neutral, card.getHeroClass());

        testCardDaoUpdate();
    }

    /** Invoked at the end of {@link #testCardDaoInsert()} */
    public void testCardDaoUpdate() {
        Card card = cardRepository.findById(TEST_ID);

        LocaleString cardName = new LocaleString();
        cardName.put(Locale.US, "Mr-Dai");
        cardName.put(Locale.CHINA, "呆呆");
        card.setName(cardName);
        cardRepository.update(card);
        card = cardRepository.findById(TEST_ID);

        assertEquals(TEST_ID, card.getCardId());
        assertEquals(cardName, card.getName());

        testCardDaoRemove();
    }

    @Test
    public void testCardDaoRemove() {
        Card card = new Card();
        card.setCardId(TEST_ID);

        cardRepository.delete(card);

        card = cardRepository.findById(TEST_ID);
        assertNull(card);
    }

    @Test
    @Ignore("The test depends on real production data. Failing on development environment is acceptable.")
    public void testCardDaoOnRealData() {
        Locale locale = new Locale("en", "US");

        // http://www.hearthhead.com/card=32/alakir-the-windlord
        Card card = cardRepository.findByHHID(32);

        assertThat(card.getName().get(locale), is("Al'Akir the Windlord"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.Classic));
        assertThat(card.getHeroClass(), is(HeroClass.Shaman));
        assertThat(card.getQuality(), is(Card.Quality.Legendary));

        // http://www.hearthhead.com/card=447/arcane-explosion
        card = cardRepository.findByHHID(447);

        assertThat(card.getName().get(locale), is("Arcane Explosion"));
        assertThat(card.getType(), is(Card.Type.Spell));
        assertThat(card.getSet(), is(Card.Set.Basic));
        assertThat(card.getHeroClass(), is(HeroClass.Mage));
        assertThat(card.getQuality(), is(Card.Quality.Free));

        // http://www.hearthhead.com/card=1805/deaths-bite
        card = cardRepository.findByHHID(1805);

        assertThat(card.getName().get(locale), is("Death's Bite"));
        assertThat(card.getType(), is(Card.Type.Weapon));
        assertThat(card.getSet(), is(Card.Set.Naxxramas));
        assertThat(card.getHeroClass(), is(HeroClass.Warrior));
        assertThat(card.getQuality(), is(Card.Quality.Common));

        // http://www.hearthhead.com/card=2826/alleria-windrunner
        card = cardRepository.findByHHID(2826);

        assertThat(card.getName().get(locale), is("Alleria Windrunner"));
        assertThat(card.getType(), is(Card.Type.Hero));
        assertThat(card.getSet(), is(Card.Set.AlternativeHeros));
        assertThat(card.getHeroClass(), is(HeroClass.Hunter));
        assertThat(card.getQuality(), is(Card.Quality.Epic));

        // http://www.hearthhead.com/card=1123/shapeshift
        card = cardRepository.findByHHID(1123);

        assertThat(card.getName().get(locale), is("Shapeshift"));
        assertThat(card.getType(), is(Card.Type.HeroPower));
        assertThat(card.getHeroClass(), is(HeroClass.Druid));
        assertThat(card.getQuality(), is(Card.Quality.Free));

        // http://www.hearthhead.com/card=1997/fel-cannon
        card = cardRepository.findByHHID(1997);

        assertThat(card.getName().get(locale), is("Fel Cannon"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.GoblinsVsGnomes));
        assertThat(card.getRace(), is(Card.Race.Mech));
        assertThat(card.getHeroClass(), is(HeroClass.Warlock));
        assertThat(card.getQuality(), is(Card.Quality.Rare));

        // http://www.hearthhead.com/card=2655/murloc-knight
        card = cardRepository.findByHHID(2655);

        assertThat(card.getName().get(locale), is("Murloc Knight"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.TheGrandTournament));
        assertThat(card.getRace(), is(Card.Race.Murloc));
        assertThat(card.getHeroClass(), is(HeroClass.Paladin));
        assertThat(card.getQuality(), is(Card.Quality.Common));

        // http://www.hearthhead.com/card=1990/one-eyed-cheat
        card = cardRepository.findByHHID(1990);

        assertThat(card.getName().get(locale), is("One-eyed Cheat"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.GoblinsVsGnomes));
        assertThat(card.getRace(), is(Card.Race.Pirate));
        assertThat(card.getHeroClass(), is(HeroClass.Rogue));
        assertThat(card.getQuality(), is(Card.Quality.Rare));

        // http://www.hearthhead.com/card=2286/twilight-whelp
        card = cardRepository.findByHHID(2286);

        assertThat(card.getName().get(locale), is("Twilight Whelp"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.BlackrockMountain));
        assertThat(card.getRace(), is(Card.Race.Dragon));
        assertThat(card.getHeroClass(), is(HeroClass.Priest));
        assertThat(card.getQuality(), is(Card.Quality.Common));

        // http://www.hearthhead.com/card=858/gelbin-mekkatorque
        card = cardRepository.findByHHID(858);

        assertThat(card.getName().get(locale), is("Gelbin Mekkatorque"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.Promotion));
        assertThat(card.getRace(), is(Card.Race.None));
        assertThat(card.getHeroClass(), is(HeroClass.Neutral));
        assertThat(card.getQuality(), is(Card.Quality.Legendary));

        // http://www.hearthhead.com/card=530/captains-parrot
        card = cardRepository.findByHHID(530);

        assertThat(card.getName().get(locale), is("Captain's Parrot"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.Reward));
        assertThat(card.getRace(), is(Card.Race.Beast));
        assertThat(card.getHeroClass(), is(HeroClass.Neutral));
        assertThat(card.getQuality(), is(Card.Quality.Epic));

        // http://www.hearthhead.com/card=1986/malganis
        card = cardRepository.findByHHID(1986);

        assertThat(card.getName().get(locale), is("Mal'Ganis"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.GoblinsVsGnomes));
        assertThat(card.getRace(), is(Card.Race.Demon));
        assertThat(card.getHeroClass(), is(HeroClass.Warlock));
        assertThat(card.getQuality(), is(Card.Quality.Legendary));

        // http://www.hearthhead.com/card=2007/vitality-totem
        card = cardRepository.findByHHID(2007);

        assertThat(card.getName().get(locale), is("Vitality Totem"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.GoblinsVsGnomes));
        assertThat(card.getRace(), is(Card.Race.Totem));
        assertThat(card.getHeroClass(), is(HeroClass.Shaman));
        assertThat(card.getQuality(), is(Card.Quality.Rare));

        // http://www.hearthhead.com/card=1671/shado-pan-monk
        card = cardRepository.findByHHID(1671);

        assertThat(card.getName().get(locale), is("Shado-Pan Monk"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.Missions));
        assertThat(card.getRace(), is(Card.Race.None));
        assertThat(card.getHeroClass(), is(HeroClass.Neutral));
        assertThat(card.getQuality(), is(Card.Quality.Common));
        assertThat(card.getCollectible(), is(false));

        // http://www.hearthhead.com/card=1768/ben-brode
        card = cardRepository.findByHHID(1768);

        assertThat(card.getName().get(locale), is("Ben Brode"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.Credits));
        assertThat(card.getRace(), is(Card.Race.None));
        assertThat(card.getHeroClass(), is(HeroClass.Neutral));
        assertThat(card.getQuality(), is(Card.Quality.Legendary));
        assertThat(card.getCollectible(), is(false));

        // http://www.hearthhead.com/card=2690/golemagg
        card = cardRepository.findByHHID(2690);

        assertThat(card.getName().get(locale), is("Golemagg"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.TavernBrawl));
        assertThat(card.getRace(), is(Card.Race.None));
        assertThat(card.getHeroClass(), is(HeroClass.Neutral));
        assertThat(card.getQuality(), is(Card.Quality.Legendary));
        assertThat(card.getCollectible(), is(false));

        // http://www.hearthhead.com/card=2951/elise-starseeker
        card = cardRepository.findByHHID(2951);

        assertThat(card.getName().get(locale), is("Elise Starseeker"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.LeagueOfExplorers));
        assertThat(card.getRace(), is(Card.Race.None));
        assertThat(card.getHeroClass(), is(HeroClass.Neutral));
        assertThat(card.getQuality(), is(Card.Quality.Legendary));
        assertThat(card.getCollectible(), is(true));

        // http://www.hearthhead.com/card=38857/cthun
        card = cardRepository.findByHHID(38857);

        assertThat(card.getName().get(locale), is("C'Thun"));
        assertThat(card.getType(), is(Card.Type.Minion));
        assertThat(card.getSet(), is(Card.Set.WhisperOfTheOldGods));
        assertThat(card.getRace(), is(Card.Race.None));
        assertThat(card.getHeroClass(), is(HeroClass.Neutral));
        assertThat(card.getQuality(), is(Card.Quality.Legendary));
        assertThat(card.getCollectible(), is(true));
    }

}
