package com.hearthintellect.repository;

import com.hearthintellect.model.Token;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface TokenRepository extends CrudRepository<Token, String> {
    Token findByUsername(String username);

    long countByIdAndUsernameAndExpireTimeGreaterThan(String id, String username, LocalDateTime expireTime);

    default boolean tokenMatches(String token, String username) {
        return countByIdAndUsernameAndExpireTimeGreaterThan(token, username, LocalDateTime.now()) > 0;
    }

    default boolean isAdmin(String token) {
        return tokenMatches(token, "admin");
    }
}
