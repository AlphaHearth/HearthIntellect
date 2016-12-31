package com.hearthintellect.repository.morphia;

import com.hearthintellect.model.User;
import com.hearthintellect.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Iterator;

@Repository
public class UserRepositoryImpl extends MorphiaRepository<String, User> implements UserRepository {
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
