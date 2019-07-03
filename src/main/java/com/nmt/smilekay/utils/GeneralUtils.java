package com.nmt.smilekay.utils;

import java.util.Random;

public class GeneralUtils {
    /*
     * 生成随机loginCode
     */
    public static String getRandomName() {
        Random rm = new Random();
        double pross = (1 + rm.nextDouble()) * Math.pow(10, 6);
        String fixLenthString = String.valueOf(pross);
        return "u" + fixLenthString.substring(1, 7);
    }
}
