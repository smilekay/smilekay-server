package com.nmt.smilekay.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
@Data
public class NewsResponse implements Serializable {
    private int status;
    private String msg;
    private NewsResult result;
}
