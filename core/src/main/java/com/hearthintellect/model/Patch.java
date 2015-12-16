package com.hearthintellect.model;

import org.json.JSONObject;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.utils.IndexType;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity(value = "patches", noClassnameStored = true)
@Indexes({
         @Index(name = "releaseDate", fields = @Field(value = "releaseDate", type = IndexType.DESC))
})
public class Patch extends MongoEntity<String> implements JsonEntity {

    private String patchCode;
    private ZonedDateTime releaseDate;
    private String releaseNote;

    @Override
    public JSONObject toJson() {
        JSONObject result = new JSONObject();

        result.put("id", patchCode);
        result.put("releaseDate", releaseDate.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
        result.put("releaseNote", releaseNote);

        return result;
    }

    @Override
    public String getId() {
        return patchCode;
    }
    @Override
    public void setId(String id) {
        patchCode = id;
    }
    public ZonedDateTime getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(ZonedDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }
    public String getReleaseNote() {
        return releaseNote;
    }
    public void setReleaseNote(String releaseNote) {
        this.releaseNote = releaseNote;
    }
}
