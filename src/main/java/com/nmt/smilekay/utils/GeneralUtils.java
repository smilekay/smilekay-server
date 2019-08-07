package com.nmt.smilekay.utils;

import java.util.Random;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
public class GeneralUtils {
    /**
     * 生成随机loginCode
     *
     * @return
     */
    public static String getRandomName() {
        Random rm = new Random();
        double pross = (1 + rm.nextDouble()) * Math.pow(10, 6);
        String fixLenthString = String.valueOf(pross);
        return "u" + fixLenthString.substring(1, 7);
    }
}
