package com.hearthintellect.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.hearthintellect.model.Patch;

import java.lang.reflect.Type;

public class PatchDeserializer implements JsonDeserializer<Patch> {
    @Override
    public Patch deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            int receivedPatchNum = json.getAsInt();
            return new Patch(receivedPatchNum, null);
        } catch (Throwable ex) {
            throw new JsonParseException("Cannot parse given JSON element `" + json + "` as Patch.", ex);
        }
    }
}
