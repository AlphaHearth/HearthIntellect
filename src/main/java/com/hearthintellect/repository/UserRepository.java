package com.hearthintellect.repository;

import com.hearthintellect.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {}
