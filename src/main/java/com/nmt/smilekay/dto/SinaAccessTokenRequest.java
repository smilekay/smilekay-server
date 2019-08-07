package com.nmt.smilekay.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
@Getter
@Setter
public class SinaAccessTokenRequest {
    private String app_key;
    private String app_secret;
    private String redirect_uri;
    private String code;
}
