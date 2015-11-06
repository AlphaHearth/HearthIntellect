package com.hearthintellect.model;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Entity for Deck
 * @author Robert Peng
 */
@Entity(value = "decks", noClassnameStored = true)
public class Deck {

	@Id
	private long deckId;

	private String name;
	private int[] rate;

    @Embedded(concreteClass = ArrayList.class)
	private List<DeckEntry> cards;

	public String toString() {
		return "{deckId:" + deckId + ", name: " + name + "}";
	}

	public long getDeckId() {
		return deckId;
	}

	public void setDeckId(long deckId) {
		this.deckId = deckId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getRate() {
		return rate;
	}

	public void setRate(int[] rate) {
		this.rate = rate;
	}

	public List<DeckEntry> getCards() {
		return cards;
	}

	public void setCards(List<DeckEntry> cards) {
		this.cards = cards;
	}
}
