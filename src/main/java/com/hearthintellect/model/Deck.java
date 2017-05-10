package com.hearthintellect.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity for Deck
 * @author Robert Peng
 */
@Document(collection = "decks")
public class Deck {

	private @Id Long deckId;

	private String name;
    private String content;
	private int like;
    private int dislike;

	private @Field("class") HeroClass heroClass;

	private List<DeckEntry> cards;

    @DBRef
	private Patch patch;
    private boolean effective = true;

    @DBRef
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

    /* Getters and Setters */
    public Long getDeckId() { return deckId;  }
    public void setDeckId(Long deckId) { this.deckId = deckId;  }
    public String getName() { return name;  }
    public void setName(String name) { this.name = name;  }
    public String getContent() { return content;  }
    public void setContent(String content) { this.content = content;  }
    public int getLike() { return like;  }
    public void setLike(int like) { this.like = like;  }
    public int getDislike() { return dislike;  }
    public void setDislike(int dislike) { this.dislike = dislike;  }
    public HeroClass getHeroClass() { return heroClass;  }
    public void setHeroClass(HeroClass heroClass) { this.heroClass = heroClass;  }
    public List<DeckEntry> getCards() { return cards;  }
    public void setCards(List<DeckEntry> cards) { this.cards = cards;  }
    public Patch getPatch() { return patch;  }
    public void setPatch(Patch patch) { this.patch = patch;  }
    public User getAuthor() { return author;  }
    public void setAuthor(User author) { this.author = author;  }
    public LocalDateTime getPostedDate() { return postedDate;  }
    public void setPostedDate(LocalDateTime postedDate) { this.postedDate = postedDate;  }
    public LocalDateTime getLastModified() { return lastModified;  }
    public void setLastModified(LocalDateTime lastModified) { this.lastModified = lastModified;  }
    public boolean getEffective() { return effective;  }
    public void setEffective(boolean effective) { this.effective = effective; } }
