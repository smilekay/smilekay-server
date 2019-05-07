package com.nmt.smilekay.service;

import com.nmt.smilekay.entity.TbUser;

public interface LoginService {
    TbUser login(String loginCode,String password);

    void logout();

    int register(TbUser tbUser);
}
