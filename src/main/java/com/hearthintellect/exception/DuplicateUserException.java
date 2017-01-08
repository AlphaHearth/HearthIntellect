package com.hearthintellect.exception;

public class DuplicateUserException extends DuplicateEntityException {
    public DuplicateUserException(String username) {
        super("User", username);
    }
}
