package com.nmt.smilekay.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

public class SkPasswordEncoder implements PasswordEncoder {
    private static SkPasswordEncoder instance = new SkPasswordEncoder();

    private SkPasswordEncoder() {
    }

    public static SkPasswordEncoder getInstance() {
        return instance;
    }

    @Override
    public String encode(CharSequence charSequence) {
        return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(DigestUtils.md5DigestAsHex(charSequence.toString().getBytes()));
    }
}
