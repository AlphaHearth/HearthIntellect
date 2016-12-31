package com.hearthintellect.repository;

import com.hearthintellect.model.MongoEntity;

public interface Repository<S, T extends MongoEntity<S>> {
    T findById(S id);
    void insert(T t);
    void update(T t);
    void delete(T t);
}
