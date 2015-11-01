package com.hearthintellect.model;

import org.mongodb.morphia.annotations.*;

@Entity(value = "mechanics", noClassnameStored = true)
@Indexes(@Index(name = "name", fields = @Field("name")))
public class Mechanic {

	@Id
	long mechanicId;

	String name;
	String description;
}
