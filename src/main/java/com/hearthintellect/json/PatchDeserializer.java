package com.hearthintellect.json;

import com.google.gson.*;
import com.hearthintellect.model.Patch;
import com.hearthintellect.utils.LocaleString;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class PatchDeserializer implements JsonDeserializer<Patch> {
    @Override
    public Patch deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (json.isJsonPrimitive()) {
            try {
                return new Patch(json.getAsInt(), null);
            } catch (Exception ex) {
                throw new JsonParseException("Failed to parse `" + json + "` as Patch.");
            }
        } else if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            Patch result = new Patch();
            if (jsonObject.has("buildNum"))
                result.setBuildNum(jsonObject.get("buildNum").getAsInt());
            if (jsonObject.has("patchCode"))
                result.setPatchCode(jsonObject.get("patchCode").getAsString());
            if (jsonObject.has("releaseDate"))
                result.setReleaseDate(context.deserialize(jsonObject.get("releaseDate"), LocalDate.class));
            if (jsonObject.has("releaseNote"))
                result.setReleaseNote(context.deserialize(jsonObject.get("releaseNote"), LocaleString.class));
            return result;
        }
        throw new JsonParseException("Failed to parse `" + json + "` as Patch.");
    }
}
