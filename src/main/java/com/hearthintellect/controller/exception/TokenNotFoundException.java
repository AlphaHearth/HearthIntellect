package com.hearthintellect.controller.exception;

public class TokenNotFoundException extends NotFoundException {
    public TokenNotFoundException(String tokenID) {
        super("Token", tokenID);
    }
}
