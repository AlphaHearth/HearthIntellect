package com.hearthintellect.repository;

import com.hearthintellect.model.User;

public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);
}
