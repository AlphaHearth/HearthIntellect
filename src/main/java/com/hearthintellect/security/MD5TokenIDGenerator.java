package com.hearthintellect.security;

import com.hearthintellect.model.Token;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

public class MD5TokenIDGenerator implements TokenIDGenerator {
    private final Random random = new SecureRandom(LocalDateTime.now().toString().getBytes());

    @Override
    public String generateID(Token token) {
        return DigestUtils.md5Hex(String.format("%d:%s:%d:%s:%d",
                random.nextInt(), token.getUsername(), random.nextInt(), token.getExpireTime(), random.nextInt()));
    }
}
