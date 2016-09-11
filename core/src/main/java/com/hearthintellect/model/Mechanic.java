package com.hearthintellect.model;

import com.hearthintellect.utils.LocaleString;
import org.json.JSONObject;
import org.mongodb.morphia.annotations.*;

@Entity(value = "mechanics", noClassnameStored = true)
@Indexes(@Index(name = "name", fields = @Field("name")))
public class Mechanic extends MongoEntity<Integer> implements JsonEntity {

	@Id
    private int mechanicId;
    private int HHID;

    private LocaleString name;
    private LocaleString description;

    public int getMechanicId() {
        return mechanicId;
    }
    public void setMechanicId(int mechanicId) {
        this.mechanicId = mechanicId;
    }
    public int getHHID() {
        return HHID;
    }
    public void setHHID(int HHID) {
        this.HHID = HHID;
    }
    public LocaleString getName() {
        return name;
    }
    public void setName(LocaleString name) {
        this.name = name;
    }
    public LocaleString getDescription() {
        return description;
    }
    public void setDescription(LocaleString description) {
        this.description = description;
    }
    @Override
    public Integer getId() {
        return mechanicId;
    }
    @Override
    public void setId(Integer id) {
        mechanicId = id;
    }

    @Override
    public JSONObject toJson() {
        JSONObject result = new JSONObject();

        result.put("id", mechanicId);
        result.put("name", name);
        result.put("description", description);

        return result;
    }
}
