package com.hearthintellect.repository;

import com.hearthintellect.exception.EmptyTokenException;
import com.hearthintellect.exception.TokenInvalidOrExpiredException;
import com.hearthintellect.model.Token;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface TokenRepository extends CrudRepository<Token, String> {
    Token findByUsername(String username);

    long countByIdAndUsernameAndExpireTimeGreaterThan(String id, String username, LocalDateTime expireTime);

    default void tokenVerify(String token, String username) {
        if (StringUtils.isBlank(token))
            throw new EmptyTokenException();
        long count = countByIdAndUsernameAndExpireTimeGreaterThan(token, username, LocalDateTime.now());
        if (count == 0)
            throw new TokenInvalidOrExpiredException(token);
    }

    default void adminVerify(String token) {
        tokenVerify(token, "admin");
    }
}
