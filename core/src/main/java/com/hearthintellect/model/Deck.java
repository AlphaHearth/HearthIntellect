package com.hearthintellect.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.annotations.*;
import org.mongodb.morphia.utils.IndexType;

/**
 * Entity for Deck
 * @author Robert Peng
 */
@Entity(value = "decks", noClassnameStored = true)
@Indexes({
         @Index(fields = @Field(value = "$name", type = IndexType.TEXT)),
         @Index(fields = @Field("class"))
})
public class Deck extends MongoEntity<Long> {

	@Id
	private long deckId;

	private String name;
    private String content;
	private int like;
    private int dislike;
    @Property("class")
	private HeroClass classs;
    @Embedded(concreteClass = ArrayList.class)
	private List<DeckEntry> cards;

    private LocalDateTime postedDate;
    private LocalDateTime lastModified;

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
	public List<DeckEntry> getCards() {
		return cards;
	}
	public void setCards(List<DeckEntry> cards) {
		this.cards = cards;
	}
	@Override
	public Long getId() {
		return deckId;
	}
	@Override
	public void setId(Long id) {
		deckId = id;
	}
    public HeroClass getClasss() {
        return classs;
    }
    public void setClasss(HeroClass classs) {
        this.classs = classs;
    }
    public int getLike() {
        return like;
    }
    public void setLike(int like) {
        this.like = like;
    }
    public int getDislike() {
        return dislike;
    }
    public void setDislike(int dislike) {
        this.dislike = dislike;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
