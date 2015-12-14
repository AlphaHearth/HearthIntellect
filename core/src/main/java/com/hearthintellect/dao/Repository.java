package com.hearthintellect.dao;

import com.hearthintellect.model.MongoEntity;
import org.mongodb.morphia.Datastore;

public interface Repository<T extends MongoEntity> {

    T findById(Object id);

    void insert(T t);

    void update(T t);

    void delete(T t);

    void setDatastore(Datastore datastore);

}
