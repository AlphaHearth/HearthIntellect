package com.hearthintellect.security;

import com.hearthintellect.model.Token;

public interface TokenIDGenerator {
    String generateID(Token token);

    default void setTokenID(Token token) {
        token.setID(generateID(token));
    }
}
