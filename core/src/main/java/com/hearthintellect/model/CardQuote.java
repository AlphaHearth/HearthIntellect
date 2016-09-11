package com.hearthintellect.model;

import com.hearthintellect.utils.LocaleString;
import org.json.JSONObject;
import org.mongodb.morphia.annotations.Embedded;

/**
 * Quotes of a card
 */
@Embedded
public class CardQuote implements JsonEntity {

    private int HHID;
    private Type type;
    private LocaleString line;
    private String audioUrl;

    public CardQuote() {}

    public CardQuote(Type type, LocaleString line, String audioUrl) {
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

    public void setHHID(int HHID) { this.HHID = HHID; }
    public int getHHID() { return HHID; }
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
