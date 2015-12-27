package com.hearthintellect.model;

import org.json.JSONObject;

/**
 * Marker interface for converting a Mongo Model into {@link JSONObject}
 */
public interface JsonEntity {

    /**
     * Converts the instance into corresponding {@link JSONObject}
     *
     * @return the corresponding {@link JSONObject}
     */
    JSONObject toJson();

}
