package com.nmt.smilekay.controller;

import com.nmt.smilekay.dto.BaseResult;
import com.nmt.smilekay.entity.TbUser;
import com.nmt.smilekay.service.LoginService;
import com.nmt.smilekay.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;
    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseResult login(String loginCode, String password, @RequestParam(required = false) String portal) {
        TbUser tbUser = loginService.login(loginCode, password);
        if (tbUser == null) {
            return BaseResult.notOk(null);
        } else {
            String token = UUID.randomUUID().toString();
            try {
                redisService.put(token, loginCode, 60 * 60 * 24);
            } catch (Exception e) {
                logger.error("smilekay->login->error:" + e.getMessage());
            }
        }
        logger.info("smilekay->login->success");
        return BaseResult.ok();
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public BaseResult register(String userName, String loginCode, String password, String email) {
        TbUser tbUser = new TbUser();
        tbUser.setUserName(userName);
        tbUser.setLoginCode(loginCode);
        String pwd = DigestUtils.md5DigestAsHex(password.getBytes());
        tbUser.setPassword(pwd);
        tbUser.setEmail(email);
        tbUser.setUserType("超级管理员");
        tbUser.setMgrType("1");
        tbUser.setStatus("0");
        tbUser.setCreateDate(new Date());
        tbUser.setUpdateDate(new Date());
        try {
            int result = loginService.register(tbUser);
            if (result == 1) {
                //用户已注册
                return BaseResult.notOk(null);
            }
        } catch (Exception e) {
            return BaseResult.notOk(null);
        }
        return BaseResult.ok();
    }
}