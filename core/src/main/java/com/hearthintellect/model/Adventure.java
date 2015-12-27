package com.hearthintellect.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "adventures", noClassnameStored = true)
public class Adventure extends MongoEntity<Long> {

	@Id
	long adventureId;
	
	String note;
	String name;
	String description;
	
	int adventureCategoryId;
	int modeId;
	int sortId;

	@Override
	public Long getId() {
		return adventureId;
	}

	@Override
	public void setId(Long id) {
		adventureId = id;
	}
}
