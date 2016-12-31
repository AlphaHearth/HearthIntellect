package com.hearthintellect.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * An entry for a deck, merely a tuple of `(card, count)`
 */
public class DeckEntry {

    private @DBRef Card card;
    private int count;

    public DeckEntry(Card card, int count) {
        this.card = card;
        this.count = count;
    }

    public Card getCard() { return card; }
    public void setCard(Card card) { this.card = card; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
}
