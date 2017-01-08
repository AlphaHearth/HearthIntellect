package com.hearthintellect.exception;

public class TokenInvalidOrExpiredException extends UnauthorizedException {
    public TokenInvalidOrExpiredException(String token) {
        super("The given token `" + token + "` is invalid for this API or has expired.");
    }
}
