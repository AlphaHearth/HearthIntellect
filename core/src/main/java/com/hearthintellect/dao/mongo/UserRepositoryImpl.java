package com.hearthintellect.dao.mongo;

import com.hearthintellect.dao.UserRepository;
import com.hearthintellect.model.User;

public class UserRepositoryImpl extends MorphiaRepository<User> implements UserRepository {
    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User findByEmail(String email) {
        return findById(email);
    }
}
