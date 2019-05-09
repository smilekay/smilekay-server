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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        String token = UUID.randomUUID().toString();
        if (tbUser == null) {
            return BaseResult.notOk(1, "用户不存在");
        } else {
            try {
                redisService.put(token, loginCode, 60 * 60 * 24);
            } catch (Exception e) {
                logger.error("smilekay->login->error:" + e.getMessage());
                return BaseResult.notOk(-1, "登录失败");
            }
        }
        logger.info("smilekay->login->success");
        return BaseResult.ok(token, "登录成功");
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public BaseResult register(String userName, String loginCode, String password, String email) {
        logger.info("smilekay->register->loginCode:" + loginCode);
        TbUser tbUser = new TbUser();
        tbUser.setUserName(userName);
        tbUser.setLoginCode(loginCode);
        String pwd = DigestUtils.md5DigestAsHex(password.getBytes());
        tbUser.setPassword(pwd);
        tbUser.setEmail(email);
        tbUser.setUserType("1");
        tbUser.setMgrType("1");
        tbUser.setStatus("0");
        tbUser.setCreateBy("admin");
        tbUser.setCreateDate(new Date());
        tbUser.setUpdateBy("admin");
        tbUser.setUpdateDate(new Date());
        try {
            int result = loginService.register(tbUser);
            if (result == 1) {
                return BaseResult.notOk(1, "用户已注册");
            }
        } catch (Exception e) {
            logger.error("smilekay->register->error:" + e.getMessage());
            return BaseResult.notOk(-1, "注册失败");
        }
        return BaseResult.ok(0, "注册成功");
    }

    @RequestMapping("logout")
    public BaseResult logout(String token) {
        logger.error("smilekay->logout->token:" + token);
        String loginCode = (String) redisService.get(token);
        if (loginCode != null) {
            boolean result = redisService.delete(token);
            if (result) {
                result = redisService.delete(loginCode);
                if (result) {
                    return BaseResult.ok(0, "注销成功");
                }
            }
        }
        return BaseResult.notOk(1, "注销失败");
    }

    @RequestMapping("check_login")
    public BaseResult checkLogin(String token) {
        String loginCode = (String) redisService.get(token);
        if (loginCode != null) {
            String json = (String) redisService.get(loginCode);
            if (json != null) {
                return BaseResult.ok(0,"用户已登录");
            }
        }
        return BaseResult.notOk(-1,"用户未登录");
    }
}
