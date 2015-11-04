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

    public CardQuote() {}

    public CardQuote(Type type, String line, String audioUrl) {
        this.type = type;
        this.line = line;
        this.audioUrl = audioUrl;
    }

    public enum Type {
        Play, Attack, Death, Alternate, Trigger, Other
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
}
