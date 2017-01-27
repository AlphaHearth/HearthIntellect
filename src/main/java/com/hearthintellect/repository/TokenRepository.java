package com.hearthintellect.repository;

import com.hearthintellect.model.Token;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

import static com.hearthintellect.exception.Exceptions.emptyTokenException;
import static com.hearthintellect.exception.Exceptions.invalidOrExpiredTokenException;

public interface TokenRepository extends CrudRepository<Token, String> {
    Token findByUsername(String username);

    long countByIdAndUsernameAndExpireTimeGreaterThan(String id, String username, LocalDateTime expireTime);

    default void tokenVerify(String token, String username) {
        if (StringUtils.isBlank(token))
            throw emptyTokenException();
        long count = countByIdAndUsernameAndExpireTimeGreaterThan(token, username, LocalDateTime.now());
        if (count == 0)
            throw invalidOrExpiredTokenException(token);
    }

    default void adminVerify(String token) {
        tokenVerify(token, "admin");
    }
}
