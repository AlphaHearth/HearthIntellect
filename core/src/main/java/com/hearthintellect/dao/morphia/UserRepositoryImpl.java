package com.hearthintellect.dao.morphia;

import com.hearthintellect.dao.UserRepository;
import com.hearthintellect.model.User;

import java.util.Iterator;

public class UserRepositoryImpl extends MorphiaRepository<User> implements UserRepository {
    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User findByEmail(String email) {
        return findById(email);
    }

    @Override
    public Iterator<User> findAllByNickname(String nickname) {
        return createQuery().search(nickname).iterator();
    }
}
