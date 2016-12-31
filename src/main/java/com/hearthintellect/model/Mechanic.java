package com.hearthintellect.model;

import com.hearthintellect.utils.LocaleString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "mechanics")
public class Mechanic {

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
}
