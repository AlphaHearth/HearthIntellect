package com.hearthintellect.model;

import org.json.JSONObject;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

/**
 * An entry for a deck, merely a tuple of `(card, count)`
 */
@Embedded
public class DeckEntry implements JsonEntity {

    @Reference(idOnly = true)
    private Card card;
    private int count;

    public DeckEntry(Card card, int count) {
        this.card = card;
        this.count = count;
    }

    public Card getCard() { return card; }
    public void setCard(Card card) { this.card = card; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

    @Override
    public JSONObject toJson() {
        JSONObject result = new JSONObject();

        result.put("card", card.getId());
        result.put("count", count);

        return result;
    }
}
