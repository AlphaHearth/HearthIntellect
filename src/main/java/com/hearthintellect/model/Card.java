package com.hearthintellect.model;

import org.mongodb.morphia.annotations.*;

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
public class Card {

    @Id
    long cardId;
    String name;
    String effect;
    String desc;

    String imageUrl;

    CardSet set;
    CardType type;
    CardQuality quality;
    Race race;

    @Property("class")
    Class _class;

    int health;
    int attack;
    int cost;

    int[] collect;
    int[] disenchant;

    @Reference(lazy = true, idOnly = true)
    List<Mechanic> mechanics;

    public String toString() {
        return "{cardId: " + cardId + ", name: " + name + ", imageUrl: " + imageUrl + "}";
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
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

    public CardSet getSet() {
        return set;
    }

    public void setSet(CardSet set) {
        this.set = set;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardQuality getQuality() {
        return quality;
    }

    public void setQuality(CardQuality quality) {
        this.quality = quality;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Class get_class() {
        return _class;
    }

    public void set_class(Class _class) {
        this._class = _class;
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

    public int[] getCollect() {
        return collect;
    }

    public void setCollect(int[] collect) {
        this.collect = collect;
    }

    public int[] getDisenchant() {
        return disenchant;
    }

    public void setDisenchant(int[] disenchant) {
        this.disenchant = disenchant;
    }

    public List<Mechanic> getMechanics() {
        return mechanics;
    }

    public void setMechanics(List<Mechanic> mechanics) {
        this.mechanics = mechanics;
    }
}
