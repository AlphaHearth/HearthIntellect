package com.hearthintellect.model;

import com.hearthintellect.utils.LocaleString;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.time.ZonedDateTime;

@Entity(value = "patches", noClassnameStored = true)
public class Patch extends MongoEntity<Integer> {

    @Id
    private int buildNum;
    private String patchCode;
    private ZonedDateTime releaseDate;
    private LocaleString releaseNote;

    public Patch() {}

    public Patch(int buildNum, String patchCode) {
        this.buildNum = buildNum;
        this.patchCode = patchCode;
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
    public ZonedDateTime getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(ZonedDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }
    public LocaleString getReleaseNote() {
        return releaseNote;
    }
    public void setReleaseNote(LocaleString releaseNote) {
        this.releaseNote = releaseNote;
    }
}
