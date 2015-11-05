package com.hearthintellect.model;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

/**
 * An entry for a deck, merely a tuple of `(card, count)`
 */
@Embedded
public class DeckEntry {

    @Reference(idOnly = true)
    private Card card;
    private int count;

    public DeckEntry(Card card, int count) {
        this.card = card;
        this.count = count;
    }

    /**
     * Return the JSON representation of the entry
     */
    @Override
    public String toString() {
        // TODO use Gson to generate JSON

        return "";
    }

    public Card getCard() { return card; }
    public void setCard(Card card) { this.card = card; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }

}
