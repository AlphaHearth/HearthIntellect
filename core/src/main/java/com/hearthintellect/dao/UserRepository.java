package com.hearthintellect.dao;

import com.hearthintellect.model.User;

import java.util.Iterator;

public interface UserRepository extends Repository<String, User> {

    User findByEmail(String email);

    /**
     * Find all {@link User} that have the similar nickname
     *
     * @param nickname the given nickname
     * @return {@link User} that have the similar nickname
     */
    Iterator<User> findAllByNickname(String nickname);

}
