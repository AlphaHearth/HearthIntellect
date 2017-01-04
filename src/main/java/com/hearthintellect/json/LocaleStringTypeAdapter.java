package com.hearthintellect.json;

import com.google.gson.*;
import com.hearthintellect.utils.LocaleString;

import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;

public class LocaleStringTypeAdapter implements JsonSerializer<LocaleString>, JsonDeserializer<LocaleString> {
    private static final Locale DEFAULT_LOCALE = Locale.US;

    @Override
    public LocaleString deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        LocaleString result = new LocaleString();
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                if (!entry.getValue().isJsonPrimitive())
                    continue;
                result.put(Locale.forLanguageTag(entry.getKey()), entry.getValue().getAsString());
            }
            return result;
        } else if (json.isJsonPrimitive()) {
            String content = json.getAsString();
            result.put(DEFAULT_LOCALE, content);
            return result;
        }
        throw new JsonParseException("Given JSON element `" + json + "` cannot be parsed as java.util.Locale.");
    }

    @Override
    public JsonElement serialize(LocaleString src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        for (Map.Entry<Locale, String> entry : src.entrySet())
            result.addProperty(entry.getKey().toLanguageTag(), entry.getValue());
        return result;
    }
}
