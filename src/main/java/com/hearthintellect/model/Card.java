package com.hearthintellect.model;

import com.google.gson.annotations.SerializedName;
import com.hearthintellect.utils.LocaleString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Collections;
import java.util.List;

/**
 * Entity for Card
 *
 * @author Robert Peng
 */
@Document(collection = "cards")
public class Card implements Entity<String> {

    @SerializedName("id")
    private @Id String cardId;

    private LocaleString name;
    private LocaleString effect;
    private LocaleString flavor;

    private int cost;
    private int attack;
    private int health;

    private @Field("image") String imageUrl;
    private @Indexed @Field("class") HeroClass heroClass;

    private @Indexed boolean collectible;
    private @Indexed Quality quality;
    private @Indexed Type type;
    private @Indexed Set set;
    private @Indexed Race race;
    private @Indexed boolean arenaAvailable = true;

    private @DBRef Patch sincePatch;
    private @DBRef Patch addedPatch;

    @DBRef(lazy = true)
    private List<Mechanic> mechanics = Collections.emptyList();
    private List<CardQuote> quotes = Collections.emptyList();
    private List<HistoryCard> versions = Collections.emptyList();

    /* Getters and Setters */
    public String getCardId() { return cardId; }
    public void setCardId(String cardId) { this.cardId = cardId; }
    public LocaleString getName() { return name; }
    public void setName(LocaleString name) { this.name = name; }
    public LocaleString getEffect() { return effect; }
    public void setEffect(LocaleString effect) { this.effect = effect; }
    public LocaleString getFlavor() { return flavor; }
    public void setFlavor(LocaleString flavor) { this.flavor = flavor; }
    public Card.Set getSet() { return set; }
    public void setSet(Card.Set set) { this.set = set; }
    public Card.Type getType() { return type; }
    public void setType(Card.Type type) { this.type = type; }
    public Card.Quality getQuality() { return quality; }
    public void setQuality(Card.Quality quality) { this.quality = quality; }
    public Race getRace() { return race; }
    public void setRace(Race race) { this.race = race; }
    public HeroClass getHeroClass() { return heroClass; }
    public void setHeroClass(HeroClass heroClass) { this.heroClass = heroClass; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public int getAttack() { return attack; }
    public void setAttack(int attack) { this.attack = attack; }
    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }
    public boolean isCollectible() { return collectible; }
    public void setCollectible(boolean collectible) { this.collectible = collectible; }
    public List<Mechanic> getMechanics() { return mechanics; }
    public void setMechanics(List<Mechanic> mechanics) { this.mechanics = mechanics; }
    public List<CardQuote> getQuotes() { return quotes; }
    public void setQuotes(List<CardQuote> quotes) { this.quotes = quotes; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public List<HistoryCard> getVersions() { return versions; }
    public void setVersions(List<HistoryCard> versions) { this.versions = versions; }
    public Patch getSincePatch() { return sincePatch; }
    public void setSincePatch(Patch sincePatch) { this.sincePatch = sincePatch; }
    public Patch getAddedPatch() { return addedPatch; }
    public void setAddedPatch(Patch addedPatch) { this.addedPatch = addedPatch; }
    public boolean isArenaAvailable() { return arenaAvailable; }
    public void setArenaAvailable(boolean arenaAvailable) { this.arenaAvailable = arenaAvailable; }
    @Override public String getID() { return cardId; }
    @Override public void setID(String s) { cardId = s; }

    /* Static Enums */
    public enum Quality {
        FREE,
        COMMON,
        RARE,
        EPIC,
        LEGENDARY
    }
    public enum Type {
        HERO, MINION, SPELL, WEAPON,
        /** Hero Power */
        POWER
    }
    public enum Set {
        BASIC, CLASSIC, REWARD, MISSIONS, PROMOTION, CREDITS,
        /** Hero skins */
        HEROS,
        /** Tavern Brawl */
        BRAWL,
        /** Naxxramas */
        NAXX,
        /** Goblins vs. Gnomes */
        GVG,
        /** Blackrock Mountain */
        BRM,
        /** The Grand Tournament */
        TGT,
        /** League of Explorers */
        LOE,
        /** Whisper of the Old Gods */
        OG,
        /** One Night in Karazhan */
        KARAZHAN,
        /** Mean Streets of Gadgetzan */
        GADGETZAN,
        /** Journey to Un'goro */
        UNGORO
    }
    public enum Race {
        NONE, BEAST, DEMON, DRAGON, MECH, MURLOC, PIRATE, TOTEM
    }
}
