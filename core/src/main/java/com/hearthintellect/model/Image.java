package com.hearthintellect.model;

import org.json.JSONObject;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "images", noClassnameStored = true)
public class Image extends MongoEntity<String> implements JsonEntity {
    private static final String ASSETS_URL = "http://assets.hearthintellect.com/Hearthstone/";

    @Id
    private String imageUrl;

    public enum Type {
        Medium, Original, Premium
    }
    private Type type;

    @Override
    public JSONObject toJson() {
        JSONObject result = new JSONObject();

        result.put("imageUrl", imageUrl);
        result.put("type", type.name());

        return result;
    }


    public String getId() {
        return imageUrl;
    }
    public void setId(String id) {
        this.imageUrl = id;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
}
