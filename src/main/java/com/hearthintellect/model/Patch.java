package com.hearthintellect.model;

import com.hearthintellect.utils.LocaleString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "patches")
public class Patch implements Entity<Integer> {

    private @Id int buildNum;
    private String patchCode;
    private LocalDate releaseDate;
    private LocaleString releaseNote;

    public Patch() {}

    public Patch(int buildNum, String patchCode) {
        this.buildNum = buildNum;
        this.patchCode = patchCode;
    }

    /* Getters and Setters */
    public int getBuildNum() { return buildNum; }
    public void setBuildNum(int buildNum) { this.buildNum = buildNum; }
    public String getPatchCode() { return patchCode; }
    public void setPatchCode(String patchCode) { this.patchCode = patchCode; }
    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }
    public LocaleString getReleaseNote() { return releaseNote; }
    public void setReleaseNote(LocaleString releaseNote) { this.releaseNote = releaseNote; }

    @Override
    public Integer getID() {
        return buildNum;
    }

    @Override
    public void setID(Integer integer) {
        buildNum = integer;
    }
}
