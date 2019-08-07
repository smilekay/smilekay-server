package com.nmt.smilekay.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Random;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
public class SkPasswordEncoder implements PasswordEncoder {
    private static final String[] word = {
            "a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "m", "n", "p", "q", "r",
            "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H",
            "J", "K", "L", "M", "N", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    private static final String[] num = {
            "2", "3", "4", "5", "6", "7", "8", "9"
    };

    private static SkPasswordEncoder instance = new SkPasswordEncoder();

    private SkPasswordEncoder() {
    }

    public static SkPasswordEncoder getInstance() {
        return instance;
    }

    public String autoGenerationCode() {
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random(new Date().getTime());
        int length = 10;
        int position = random.nextInt(10);
        for (int i = 0; i < length; i++) {
            if (i == position) {
                stringBuffer.append(num[random.nextInt(num.length)]);
            } else {
                stringBuffer.append(word[random.nextInt(word.length)]);
            }
        }
        return stringBuffer.toString();
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
