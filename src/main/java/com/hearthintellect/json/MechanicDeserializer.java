package com.hearthintellect.json;

import com.google.gson.*;
import com.hearthintellect.model.Mechanic;
import com.hearthintellect.utils.LocaleString;

import java.lang.reflect.Type;

public class MechanicDeserializer implements JsonDeserializer<Mechanic> {
    @Override
    public Mechanic deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Mechanic result = new Mechanic();
        if (json.isJsonPrimitive()) {
            result.setMechanicId(json.getAsString());
            return result;
        } else if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            if (jsonObject.has("id"))
                result.setMechanicId(jsonObject.get("id").getAsString());
            if (jsonObject.has("name"))
                result.setName(context.deserialize(jsonObject.get("name"), LocaleString.class));
            if (jsonObject.has("description"))
                result.setDescription(context.deserialize(jsonObject.get("description"), LocaleString.class));
            return result;
        }
        throw new JsonParseException("Failed to parse `" + json + "` as Mechanic.");
    }
}
