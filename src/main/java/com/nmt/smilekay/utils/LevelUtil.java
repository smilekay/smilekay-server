package com.nmt.smilekay.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/17 22:02
 */
public class LevelUtil {
    public final static String SEPARATOR = ".";
    public final static String ROOT = "0";

    public static String calculateLevel(String parentLevel, int parentId) {
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StringUtils.join(parentLevel,SEPARATOR,parentId);
        }
    }
}
