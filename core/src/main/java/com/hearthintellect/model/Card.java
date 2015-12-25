package com.hearthintellect.model;

import org.json.JSONObject;
import org.mongodb.morphia.annotations.*;
import org.mongodb.morphia.utils.IndexType;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity for Card
 *
 * @author Robert Peng
 */
@Entity(value = "cards", noClassnameStored = true)
@Indexes({
             @Index(name = "name", fields = @Field(value = "name", type = IndexType.TEXT)),
             @Index(name = "set", fields = @Field("set")),
             @Index(name = "type", fields = @Field("type")),
             @Index(name = "quality", fields = @Field("quality")),
             @Index(name = "race", fields = @Field("race")),
             @Index(name = "class", fields = @Field("class"))
})
public class Card extends MongoEntity<Integer> implements JsonEntity {

    @Id
    private int cardId;

    // Corresponding ID in HearthHead
    private int HHID;

    private String name;
    private String effect;
    private String desc;

    private int cost;
    private int attack;
    private int health;

    @Reference(lazy = true, idOnly = true)
    private List<Image> images;

    @Property("class")
    private HeroClass heroClass;
    @Reference(idOnly = true)
    private Patch patch;

    private boolean collectible;
    private boolean disenchantable;

    private boolean active = true;

    @Reference(lazy = true, idOnly = true)
    List<Mechanic> mechanics;

    @Embedded(concreteClass = ArrayList.class)
    List<CardQuote> quotes;

    public enum Quality {
        Free, Common, Rare, Epic, Legendary
    }
    private Quality quality;

    public enum Type {
        Hero, Minion, Power, Spell, Weapon
    }
    private Type type;

    public enum Set {
        Basic, Classic, Reward, Missions, Promotion, Credits, Naxxramas, GoblinsVsGnomes, BlackrockMountain, TheGrandTournament
    }
    private Set set;

    public enum Race {
        None, Beast, Demon, Dragon, Mech, Murloc, Pirate, Totem
    }
    private Race race;

    @Override
    public JSONObject toJson() {
        JSONObject result = new JSONObject();

        result.put("id", cardId);
        result.put("HHID", HHID);
        result.put("name", name);
        result.put("effect", effect);
        result.put("desc", desc);
        result.put("cost", cost);
        result.put("attack", attack);
        result.put("health", health);
        images.forEach((image) -> result.append("images", image.toJson()));
        result.put("heroClass", heroClass.ordinal());
        result.put("collectible", collectible);
        result.put("disenchantable", disenchantable);
        result.put("patch", patch.getId());
        result.put("active", active);
        mechanics.forEach((mechanic) -> result.append("mechanics", mechanic.getId()));
        quotes.forEach((quote) -> result.append("quotes", quote.toJson()));
        result.put("quality", quality.ordinal());
        result.put("type", type.ordinal());
        result.put("set", set.ordinal());
        result.put("race", race.ordinal());

        return result;
    }

    @Override
    public Integer getId() {
        return cardId;
    }
    @Override
    public void setId(Integer id) {
        cardId = id;
    }
    public Patch getPatch() {
        return patch;
    }
    public void setPatch(Patch patch) {
        this.patch = patch;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
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
    public boolean getDisenchantable() {
        return disenchantable;
    }
    public void setDisenchantable(boolean disenchantable) {
        this.disenchantable = disenchantable;
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
    public int getHHID() {
        return HHID;
    }
    public void setHHID(int HHID) {
        this.HHID = HHID;
    }
    public List<Image> getImages() {
        return images;
    }
    public void setImages(List<Image> images) {
        this.images = images;
    }
}
