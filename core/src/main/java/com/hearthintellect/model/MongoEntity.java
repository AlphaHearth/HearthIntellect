package com.hearthintellect.model;

/**
 * Common class for MongoDB entities
 * @param <ID> Java type of the entity's ID
 */
public abstract class MongoEntity<ID> {

    public abstract ID getId();

    public abstract void setId(ID id);

}
