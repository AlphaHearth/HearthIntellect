package com.hearthintellect.model;

import org.mongodb.morphia.annotations.*;

@Entity(value = "mechanics", noClassnameStored = true)
@Indexes(@Index(name = "name", fields = @Field("name")))
public class Mechanic {

	@Id
    private long mechanicId;

    private String name;
    private String description;

    public long getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(long mechanicId) {
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
}
