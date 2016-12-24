package com.hearthintellect.model;

/**
 * Base class for MongoDB entities
 *
 * @param <T> Java type of the entity's T
 */
public abstract class MongoEntity<T> {

    public abstract T getId();

    public abstract void setId(T id);

}
