package com.hearthintellect.security;

import org.apache.commons.codec.digest.DigestUtils;

public class SaltedMD5Encoder implements PasswordEncoder {
    @Override
    public String encode(String username, String plainPassword) {
        return DigestUtils.md5Hex(username + ":" + plainPassword);
    }
}
