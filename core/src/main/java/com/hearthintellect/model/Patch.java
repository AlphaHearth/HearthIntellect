package com.hearthintellect.model;

import org.json.JSONObject;
import org.mongodb.morphia.annotations.*;
import org.mongodb.morphia.utils.IndexType;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity(value = "patches", noClassnameStored = true)
@Indexes({
         @Index(name = "releaseDate", fields = @Field(value = "releaseDate", type = IndexType.DESC))
})
public class Patch extends MongoEntity<Integer> implements JsonEntity {

    @Id
    private int buildNum;
    private String patchCode;

    public Patch() {}

    public Patch(int buildNum, String patchCode) {
        this.buildNum = buildNum;
        this.patchCode = patchCode;
    }

    @Override
    public JSONObject toJson() {
        JSONObject result = new JSONObject();

        result.put("buildNum", buildNum);
        result.put("patchCode", patchCode);

        return result;
    }

    @Override
    public Integer getId() {
        return buildNum;
    }
    @Override
    public void setId(Integer id) {
        buildNum = id;
    }
    public int getBuildNum() {
        return buildNum;
    }
    public void setBuildNum(int buildNum) {
        this.buildNum = buildNum;
    }
    public String getPatchCode() {
        return patchCode;
    }
    public void setPatchCode(String patchCode) {
        this.patchCode = patchCode;
    }
}
