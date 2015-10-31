package com.hearthintellect.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity(value = "mechanics", noClassnameStored = true)
@Indexes(@Index("name"))
public class Mechanic {

	@Id
	long mechanicId;

	String name;
	String description;
}
