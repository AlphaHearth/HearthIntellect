package com.hearthintellect.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "adventures")
public class Adventure extends BaseCollection {
	
	private int adventureId;
	
	private String note;
	private String name;
	private String description;
	
	private int adventureCategoryId;
	private int modeId;
	private int sortId;
	
	public int getAdventureId() {
		return adventureId;
	}
	
	public void setAdventureId(int adventureId) {
		this.adventureId = adventureId;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
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
	
	public int getAdventureCategoryId() {
		return adventureCategoryId;
	}
	
	public void setAdventureCategoryId(int adventureCategoryId) {
		this.adventureCategoryId = adventureCategoryId;
	}
	
	public int getModeId() {
		return modeId;
	}
	
	public void setModeId(int modeId) {
		this.modeId = modeId;
	}
	
	public int getSortId() {
		return sortId;
	}
	
	public void setSortId(int sortId) {
		this.sortId = sortId;
	}
	
}
