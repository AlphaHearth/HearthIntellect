package com.hearthintellect.security;

import com.hearthintellect.model.User;

import java.util.Objects;

public interface PasswordEncoder {

    String encode(String username, String plainPassword);

    default void encodeUserPassword(User user) {
        user.setPassword(encode(user.getUsername(), user.getPassword()));
    }

    default boolean matches(String username, String plainPassword, String encodedPassword) {
        Objects.requireNonNull(encodedPassword, "Given `encodedPassword` cannot be `null`.");
        return encodedPassword.equals(encode(username, plainPassword));
    }
}
