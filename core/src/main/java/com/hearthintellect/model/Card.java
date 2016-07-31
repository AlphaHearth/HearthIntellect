package com.hearthintellect.model;

import com.hearthintellect.util.LocaleString;
import org.json.JSONObject;
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
             @Index(name = "class", fields = @Field("class"))
})
public class Card extends MongoEntity<Integer> implements JsonEntity {

    @Id
    private int cardId;
    private int HHID;

    private LocaleString name;
    private LocaleString effect;
    private LocaleString desc;

    private int cost;
    private int attack;
    private int health;

    @Property("image")
    private String imageUrl;
    @Property("class")
    private HeroClass heroClass;

    private boolean collectible;
    private Quality quality;
    private Type type;
    private Set set;
    private Race race;
    private boolean effective = true;

    @Reference(lazy = true, idOnly = true)
    List<Mechanic> mechanics;

    @Embedded(concreteClass = ArrayList.class)
    List<CardQuote> quotes;

    @Override
    public JSONObject toJson() {
        JSONObject result = new JSONObject();

        result.put("id", cardId);
        result.put("name", name);
        result.put("effect", effect);
        result.put("desc", desc);
        result.put("cost", cost);
        result.put("attack", attack);
        result.put("health", health);
        result.put("imageUrl", imageUrl);
        result.put("heroClass", heroClass.ordinal());
        result.put("collectible", collectible);
        mechanics.forEach((mechanic) -> result.append("mechanics", mechanic.getId()));
        quotes.forEach((quote) -> result.append("quotes", quote.toJson()));
        result.put("quality", quality.ordinal());
        result.put("type", type.ordinal());
        result.put("set", set.ordinal());
        result.put("race", race.ordinal());
        result.put("effective", effective);

        return result;
    }

    @Override
    public Integer getId() { return cardId; }
    @Override
    public void setId(Integer id) { cardId = id; }
    public int getHHID() { return HHID; }
    public void setHHID(int HHID) { this.HHID = HHID; }
    public int getCardId() {
        return cardId;
    }
    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
    public LocaleString getName() {
        return name;
    }
    public void setName(LocaleString name) {
        this.name = name;
    }
    public LocaleString getEffect() {
        return effect;
    }
    public void setEffect(LocaleString effect) {
        this.effect = effect;
    }
    public LocaleString getDesc() {
        return desc;
    }
    public void setDesc(LocaleString desc) {
        this.desc = desc;
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
    public boolean getCollectible() {
        return collectible;
    }
    public void setCollectible(boolean collectible) {
        this.collectible = collectible;
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
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public boolean getEffective() { return effective; }
    public void setEffective(boolean effective) { this.effective = effective; }

    public enum Quality {
        Free, Common, Rare, Epic, Legendary
    }
    public enum Type {
        Hero, Minion, Spell, Weapon, HeroPower
    }
    public enum Set {
        Basic, Classic, Reward, Missions, Promotion, Credits, Naxxramas,
        GoblinsVsGnomes, BlackrockMountain, TheGrandTournament, LeagueOfExplorers,
        WhisperOfTheOldGods, OneNightInKarazhan
    }
    public enum Race {
        None, Beast, Demon, Dragon, Mech, Murloc, Pirate, Totem
    }
}
