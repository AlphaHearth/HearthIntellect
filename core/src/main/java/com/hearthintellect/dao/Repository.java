package com.hearthintellect.dao;

import com.hearthintellect.model.MongoEntity;
import org.mongodb.morphia.Datastore;

public interface Repository<S, T extends MongoEntity<S>> {
    T findById(S id);
    void insert(T t);
    void update(T t);
    void delete(T t);
}
