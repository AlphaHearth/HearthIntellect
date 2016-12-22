package com.hearthintellect.model;

import com.hearthintellect.utils.LocaleString;
import org.mongodb.morphia.annotations.*;

@Entity(value = "mechanics", noClassnameStored = true)
@Indexes(@Index(name = "name", fields = @Field("name")))
public class Mechanic extends MongoEntity<Integer> {

	@Id
    private int mechanicId;

    private LocaleString name;
    private LocaleString description;

    public int getMechanicId() {
        return mechanicId;
    }
    public void setMechanicId(int mechanicId) {
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
    public Integer getId() {
        return mechanicId;
    }
    @Override
    public void setId(Integer id) {
        mechanicId = id;
    }
}
