package com.hearthintellect.model;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Quotes of a card
 */
@Embedded
public class CardQuote {

    private Type type;
    private String line;
    private String audioUrl;

    public CardQuote(Type type, String line, String audioUrl) {
        this.type = type;
        this.line = line;
        this.audioUrl = audioUrl;
    }

    public enum Type {
        Play, Attack, Death, Alternate, Trigger, Other
    }

}
