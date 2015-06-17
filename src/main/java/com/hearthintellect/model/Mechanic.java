package com.hearthintellect.model;

public class Mechanic extends BaseCollection {
	
	private int mechanicId;
	private String iconUrl;
	private String name;
	private String description;
	private String umlName;
	
	public int getMechanicId() {
		return mechanicId;
	}
	
	public void setMechanicId(int mechanicId) {
		this.mechanicId = mechanicId;
	}
	
	public String getIconUrl() {
		return iconUrl;
	}
	
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
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
	
	public String getUmlName() {
		return umlName;
	}
	
	public void setUmlName(String umlName) {
		this.umlName = umlName;
	}

}
