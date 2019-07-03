package com.nmt.smilekay.service;

import com.nmt.smilekay.dto.QQUserInfo;
import com.nmt.smilekay.dto.SinaUserInfo;
import com.nmt.smilekay.entity.TbUser;

public interface LoginService {
    TbUser login(String loginCode, String password, boolean isThirdPaLogin);

    void logout();

    int register(TbUser tbUser);

    TbUser register(QQUserInfo userInfo, String openId);

    TbUser register(SinaUserInfo userInfo, String openId);
}
