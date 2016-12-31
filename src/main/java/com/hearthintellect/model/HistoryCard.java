package com.hearthintellect.model;

import com.hearthintellect.utils.LocaleString;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Cards of history version.
 */
public class HistoryCard {
    @DBRef
    private Patch sincePatch;
    private LocaleString effect;
    private int cost;
    private int attack;
    private int health;

    public HistoryCard() {}

    public HistoryCard(Card card) {
        this.effect = card.getEffect();
        this.cost = card.getCost();
        this.attack = card.getAttack();
        this.health = card.getHealth();
    }

    public HistoryCard(Patch sincePatch, LocaleString effect, int cost, int attack, int health) {
        this.sincePatch = sincePatch;
        this.effect = effect;
        this.cost = cost;
        this.attack = attack;
        this.health = health;
    }

    public Patch getSincePatch() {
        return sincePatch;
    }
    public void setSincePatch(Patch sincePatch) {
        this.sincePatch = sincePatch;
    }
    public LocaleString getEffect() {
        return effect;
    }
    public void setEffect(LocaleString effect) {
        this.effect = effect;
    }
    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
    public int getAttack() {
        return attack;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
}
