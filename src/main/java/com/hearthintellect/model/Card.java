package com.hearthintellect.model;

import org.mongodb.morphia.annotations.*;

import java.util.List;

/**
 * Entity for Card
 * @author Robert Peng
 */
@Entity(value = "cards", noClassnameStored = true)
@Indexes(
    { @Index("set"), @Index("type"), @Index("quality"), @Index("race"), @Index("class") }
)
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
}
