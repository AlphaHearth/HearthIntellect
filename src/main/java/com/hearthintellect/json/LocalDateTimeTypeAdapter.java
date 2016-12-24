package com.hearthintellect.json;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTypeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
    private final DateTimeFormatter iso8601Formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String jsonStr = json.getAsString();
        if (jsonStr == null || jsonStr.trim().isEmpty())
            return null;
        try {
            return LocalDateTime.parse(jsonStr, iso8601Formatter);
        } catch (Throwable ex) {
            throw new JsonParseException("Received illegal date time field: `" + jsonStr + "`", ex);
        }
    }

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.format(iso8601Formatter));
    }
}
