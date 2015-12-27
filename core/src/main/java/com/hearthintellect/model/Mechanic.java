package com.hearthintellect.model;

import org.json.JSONObject;
import org.mongodb.morphia.annotations.*;

@Entity(value = "mechanics", noClassnameStored = true)
@Indexes(@Index(name = "name", fields = @Field("name")))
public class Mechanic extends MongoEntity<Integer> implements JsonEntity {

	@Id
    private int mechanicId;

    private String name;
    private String description;

    public int getMechanicId() {
        return mechanicId;
    }
    public void setMechanicId(int mechanicId) {
        this.mechanicId = mechanicId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
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
