package com.hearthintellect.controller.exception;

/**
 * Specialized {@link NotFoundException} for {@link com.hearthintellect.model.User User}.
 */
public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String username) {
        super("User", username);
    }
}
