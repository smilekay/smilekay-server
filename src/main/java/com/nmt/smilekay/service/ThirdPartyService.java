package com.nmt.smilekay.service;

import com.nmt.smilekay.dto.QQUserInfo;
import com.nmt.smilekay.dto.SinaAccessToken;
import com.nmt.smilekay.dto.SinaUserInfo;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
public interface ThirdPartyService {
    String getOpenId(String accessToken);

    QQUserInfo getQQUserInfo(String accessToken, String openId);

    SinaAccessToken getSinaAccessToken(String code);

    SinaUserInfo getSinaUserInfo(String access_token, String uid);
}
