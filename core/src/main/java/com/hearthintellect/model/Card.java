package com.hearthintellect.model;

import org.mongodb.morphia.annotations.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity for Card
 *
 * @author Robert Peng
 */
@Entity(value = "cards", noClassnameStored = true)
@Indexes({
             @Index(name = "set", fields = @Field("set")),
             @Index(name = "type", fields = @Field("type")),
             @Index(name = "quality", fields = @Field("quality")),
             @Index(name = "race", fields = @Field("race")),
             @Index(name = "classs", fields = @Field("classs"))
})
public class Card {

    @Id
    private int cardId;

    // Corresponding ID in HearthHead
    private int HHID;

    private String name;
    private String effect;
    private String desc;

    private String imageUrl;

    private Card.Set set;
    private Card.Type type;
    private Card.Quality quality;
    private Race race;

    @Property("classs")
    private HeroClass heroClass;

    private int health;
    private int attack;
    private int cost;

    private boolean collect;

    @Reference(lazy = true, idOnly = true)
    List<Mechanic> mechanics;

    @Embedded(concreteClass = ArrayList.class)
    List<CardQuote> quotes;

    @Embedded(concreteClass = ArrayList.class)
    List<Locale> locales;

    public enum Quality {
        Free, Common, Rare, Epic, Legendary
    }

    public enum Type {
        Hero, Minion, HeroPower, Spell, Weapon
    }

    public enum Set {
        Basic, Classic, Reward, Missions, Promotion, Credits, Naxxramas, GoblinsVsGnomes, BlackrockMountain, TheGrandTournament
    }

    public enum Race {
        None, Beast, Demon, Dragon, Mech, Murloc, Pirate, Totem
    }

    @Embedded
    public static class Locale {
        @Property("lang")
        private Language language;
        private String name;
        private String effect;
        private String description;

        public Language getLanguage() {
            return language;
        }

        public void setLanguage(Language language) {
            this.language = language;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEffect() {
            return effect;
        }

        public void setEffect(String effect) {
            this.effect = effect;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public String toString() {
        return "{cardId: " + cardId + ", name: " + name + ", imageUrl: " + imageUrl + "}";
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Card.Set getSet() {
        return set;
    }

    public void setSet(Card.Set set) {
        this.set = set;
    }

    public Card.Type getType() {
        return type;
    }

    public void setType(Card.Type type) {
        this.type = type;
    }

    public Card.Quality getQuality() {
        return quality;
    }

    public void setQuality(Card.Quality quality) {
        this.quality = quality;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public HeroClass getHeroClass() {
        return heroClass;
    }

    public void setHeroClass(HeroClass heroClass) {
        this.heroClass = heroClass;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean getCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public List<Mechanic> getMechanics() {
        return mechanics;
    }

    public void setMechanics(List<Mechanic> mechanics) {
        this.mechanics = mechanics;
    }

    public List<CardQuote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<CardQuote> quotes) {
        this.quotes = quotes;
    }

    public int getHHID() {
        return HHID;
    }

    public void setHHID(int HHID) {
        this.HHID = HHID;
    }

    public List<Locale> getLocales() {
        return locales;
    }

    public void setLocales(List<Locale> locales) {
        this.locales = locales;
    }
}
