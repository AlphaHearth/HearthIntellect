package com.hearthintellect.model;

import org.mongodb.morphia.annotations.*;
import org.mongodb.morphia.utils.IndexType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity for Deck
 * @author Robert Peng
 */
@Entity(value = "decks", noClassnameStored = true)
@Indexes({
         @Index(fields = @Field(value = "name", type = IndexType.TEXT)),
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
	private HeroClass heroClass;

    @Embedded(concreteClass = ArrayList.class)
	private List<DeckEntry> cards;

    @Reference(idOnly = true)
	private Patch patch;
    private boolean effective = true;

    @Reference(idOnly = true)
    private User author;

    private LocalDateTime postedDate;
    private LocalDateTime lastModified;

    public Deck() {}

    public Deck(String name, String content, Patch patch, User author,
                HeroClass heroClass, List<DeckEntry> cards) {
        this.name = name;
        this.content = content;
        this.heroClass = heroClass;
        this.cards = cards;
        this.patch = patch;
        this.author = author;

        this.postedDate = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
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
    public HeroClass getHeroClass() {
        return heroClass;
    }
    public void setHeroClass(HeroClass heroClass) {
        this.heroClass = heroClass;
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
    public boolean getEffective() {
        return effective;
    }
    public void setEffective(boolean effective) {
        this.effective = effective;
    }
    public Patch getPatch() {
        return patch;
    }
    public void setPatch(Patch patch) {
        this.patch = patch;
    }
    public LocalDateTime getPostedDate() {
        return postedDate;
    }
    public void setPostedDate(LocalDateTime postedDate) {
        this.postedDate = postedDate;
    }
    public LocalDateTime getLastModified() {
        return lastModified;
    }
    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }
    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    @Override
    public Long getId() {
        return deckId;
    }
    @Override
    public void setId(Long id) {
        deckId = id;
    }
}
