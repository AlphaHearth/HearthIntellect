package com.hearthintellect.controller.exception;

public class TokenInvalidOrExpiredException extends UnauthorizedException {
    public TokenInvalidOrExpiredException(String token) {
        super("The given token `" + token + "` is invalid or has expired.");
    }
}
