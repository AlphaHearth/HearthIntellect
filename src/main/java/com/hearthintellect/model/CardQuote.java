package com.hearthintellect.model;

import com.hearthintellect.utils.LocaleString;

/**
 * Quotes of a card
 */
public class CardQuote {

    private Type type;
    private LocaleString line;
    private String audioUrl;

    public CardQuote() {}

    public CardQuote(Type type, LocaleString line, String audioUrl) {
        this.type = type;
        this.line = line;
        this.audioUrl = audioUrl;
    }

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public LocaleString getLine() {
        return line;
    }
    public void setLine(LocaleString line) {
        this.line = line;
    }
    public String getAudioUrl() {
        return audioUrl;
    }
    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public enum Type {
        Play, Attack, Death, Alternate, Trigger, Other
    }
}