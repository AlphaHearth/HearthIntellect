package com.hearthintellect.model;

import com.hearthintellect.utils.LocaleString;
import org.mongodb.morphia.annotations.*;

@Entity(value = "mechanics", noClassnameStored = true)
@Indexes(@Index(name = "name", fields = @Field("name")))
public class Mechanic extends MongoEntity<String> {

	@Id
    private String mechanicId;

    private LocaleString name;
    private LocaleString description;

    public String getMechanicId() {
        return mechanicId;
    }
    public void setMechanicId(String mechanicId) {
        this.mechanicId = mechanicId;
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
    public String getId() {
        return mechanicId;
    }
    @Override
    public void setId(String id) {
        mechanicId = id;
    }
}
