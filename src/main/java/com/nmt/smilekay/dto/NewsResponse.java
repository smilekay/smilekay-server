package com.nmt.smilekay.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class NewsResponse implements Serializable {
    private int status;
    private String msg;
    private NewsResult result;
}
