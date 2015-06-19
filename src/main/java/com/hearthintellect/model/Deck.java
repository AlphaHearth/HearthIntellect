package com.hearthintellect.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity for Deck
 * @author Robert Peng
 */
@Document(collection="decks")
public class Deck extends BaseCollection {
	
	private int deckId;
	private String name;
	private int goodRating;
	private int badRating;
	private int occurredTimes;
	
	public String toString() {
		return "{deckId:" + deckId + ", name: " + name + "}";
	}
	
	public int getDeckId() {
		return deckId;
	}
	
	public void setDeckId(int deckId) {
		this.deckId = deckId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getGoodRating() {
		return goodRating;
	}

	public void setGoodRating(int goodRating) {
		this.goodRating = goodRating;
	}

	public int getBadRating() {
		return badRating;
	}

	public void setBadRating(int badRating) {
		this.badRating = badRating;
	}

	public int getOccurredTimes() {
		return occurredTimes;
	}

	public void setOccurredTimes(int occurredTimes) {
		this.occurredTimes = occurredTimes;
	}

	public class DeckEntry {
		private int cardId;
		private int count;
		
		public int getCardId() {
			return cardId;
		}
		
		public void setCardId(int cardId) {
			this.cardId = cardId;
		}
		
		public int getCount() {
			return count;
		}
		
		public void setCount(int count) {
			this.count = count;
		}
	}
	
	private List<DeckEntry> cards;
	
	public List<DeckEntry> getCards() {
		return cards;
	}
	
	public void setCards(List<DeckEntry> cards) {
		this.cards = cards;
	}
	
}
