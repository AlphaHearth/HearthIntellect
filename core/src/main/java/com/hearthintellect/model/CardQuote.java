package com.hearthintellect.model;

import org.json.JSONObject;
import org.mongodb.morphia.annotations.Embedded;

/**
 * Quotes of a card
 */
@Embedded
public class CardQuote implements JsonEntity {

    private Type type;
    private String line;
    private String audioUrl;

    public CardQuote() {}

    public CardQuote(Type type, String line, String audioUrl) {
        this.type = type;
        this.line = line;
        this.audioUrl = audioUrl;
    }

    @Override
    public JSONObject toJson() {
        JSONObject result = new JSONObject();

        result.put("type", type.ordinal());
        result.put("line", line);
        result.put("audioUrl", audioUrl);

        return result;
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

    public enum Type {
        Play, Attack, Death, Alternate, Trigger, Other
    }
}
