package com.hearthintellect.model;

import com.google.gson.annotations.SerializedName;
import com.hearthintellect.utils.LocaleString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "mechanics")
public class Mechanic implements Entity<String> {

	@SerializedName("id")
    private @Id String mechanicId;

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
    public String getID() {
        return mechanicId;
    }

    @Override
    public void setID(String s) {
        this.mechanicId = s;
    }
}
