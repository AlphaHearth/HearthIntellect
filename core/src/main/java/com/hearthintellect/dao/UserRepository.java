package com.hearthintellect.dao;

import com.hearthintellect.model.User;

public interface UserRepository extends Repository<User> {

    User findByEmail(String email);

}
