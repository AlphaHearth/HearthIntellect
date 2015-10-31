package com.hearthintellect.model;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

/**
 * Entity for Deck
 * @author Robert Peng
 */
@Entity(value = "decks", noClassnameStored = true)
public class Deck {

	@Id
	long deckId;

	String name;
	int[] rate;

    @Embedded(concreteClass = Entry.class)
	List<Entry> cards;
    
	public class Entry {
		@Reference(lazy = true, idOnly = true)
		Card card;
		int count;
	}

	public String toString() {
		return "{deckId:" + deckId + ", name: " + name + "}";
	}
	
}
