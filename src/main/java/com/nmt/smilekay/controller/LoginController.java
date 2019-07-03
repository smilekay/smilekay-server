package com.nmt.smilekay.controller;

import com.nmt.smilekay.dto.BaseResult;
import com.nmt.smilekay.dto.QQUserInfo;
import com.nmt.smilekay.dto.SinaAccessToken;
import com.nmt.smilekay.dto.SinaUserInfo;
import com.nmt.smilekay.utils.SkPasswordEncoder;
import com.nmt.smilekay.entity.TbUser;
import com.nmt.smilekay.service.LoginService;
import com.nmt.smilekay.service.RedisService;
import com.nmt.smilekay.service.TbUserService;
import com.nmt.smilekay.service.ThirdPartyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.UUID;

import static com.nmt.smilekay.dto.BaseResult.*;

@RestController
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ThirdPartyService thirdPartyService;
    @Autowired
    private TbUserService tbUserService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseResult login(String loginCode, String password, @RequestParam(required = false) String portal) {
        TbUser tbUser = loginService.login(loginCode, password, false);
        String token = UUID.randomUUID().toString();
        if (tbUser == null) {
            return BaseResult.notOk(1, USER_NOT_EXIST);
        } else {
            try {
                redisService.put(token, loginCode, 60 * 60 * 24);
            } catch (Exception e) {
                logger.error("smilekay->login->error:" + e.getMessage());
                return BaseResult.notOk(-1, LOGIN_FAIL);
            }
        }
        logger.info("smilekay->login->success");
        return BaseResult.ok(token, LOGIN_SUCCESS);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public BaseResult register(String userName, String loginCode, String password, String email) {
        logger.info("smilekay->register->loginCode:" + loginCode);
        TbUser tbUser = TbUser.builder()
                .userName(userName)
                .loginCode(loginCode)
                .password(SkPasswordEncoder.getInstance().encode(password))
                .email(email)
                .build();
        try {
            int result = loginService.register(tbUser);
            if (result == 1) {
                return BaseResult.notOk(1, ALREADY_REGISTER);
            }
        } catch (Exception e) {
            logger.error("smilekay->register->error:" + e.getMessage());
            return BaseResult.notOk(-1, REGISTER_FAIL);
        }
        return BaseResult.ok(0, REGISTER_SUCCESS);
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
                    return BaseResult.ok(0, LOGOUT_SUCCESS);
                }
            }
        }
        return BaseResult.notOk(1, LOGOUT_FAIL);
    }

    @RequestMapping("check_login")
    public BaseResult checkLogin(String token) {
        String loginCode = (String) redisService.get(token);
        if (loginCode != null) {
            String json = (String) redisService.get(loginCode);
            if (json != null) {
                return BaseResult.ok(0, ALREADY_LOGIN);
            }
        }
        return BaseResult.notOk(-1, NOT_LOGIN);
    }

    @RequestMapping("login/qq")
    public BaseResult qqLogin(String accessToken) {
        String openId = thirdPartyService.getOpenId(accessToken);
        Example example = new Example(TbUser.class);
        example.createCriteria().andEqualTo("qqOpenid", openId);
        TbUser tbUser = (TbUser) tbUserService.selectOneByExample(example);
        if (tbUser == null) {
            //用户不存在，先注册再登陆
            QQUserInfo userInfo = thirdPartyService.getQQUserInfo(accessToken, openId);
            logger.info("smilekay->qqLogin->userInfo:" + userInfo.toString());
            tbUser = loginService.register(userInfo, openId);
        }
        loginService.login(tbUser.getLoginCode(), tbUser.getPassword(), true);
        String token = UUID.randomUUID().toString();

        try {
            redisService.put(token, tbUser.getLoginCode(), 60 * 60 * 24);
        } catch (Exception e) {
            logger.error("smilekay->qqLogin->error:" + e.getMessage());
            return BaseResult.notOk(-1, LOGIN_FAIL);
        }
        logger.info("smilekay->qqLogin->success");
        return BaseResult.ok(token, LOGIN_SUCCESS);
    }

    @RequestMapping("login/sina")
    public BaseResult sinaLogin(String code) {
        SinaAccessToken sinaAccessToken = thirdPartyService.getSinaAccessToken(code);
        if (sinaAccessToken != null) {
            Example example = new Example(TbUser.class);
            example.createCriteria().andEqualTo("sinaOpenid", String.valueOf(sinaAccessToken.getUid()));
            TbUser tbUser = (TbUser) tbUserService.selectOneByExample(example);
            if (tbUser == null) {
                //用户不存在，先注册再登陆
                SinaUserInfo sinaUserInfo = thirdPartyService.getSinaUserInfo(sinaAccessToken.getAccess_token(), sinaAccessToken.getUid());
                logger.info("smilekay->sinaLogin->userInfo:" + sinaUserInfo.toString());
                tbUser = loginService.register(sinaUserInfo, sinaAccessToken.getUid());
            }
            loginService.login(tbUser.getLoginCode(), tbUser.getPassword(), true);
            String token = UUID.randomUUID().toString();

            try {
                redisService.put(token, tbUser.getLoginCode(), 60 * 60 * 24);
            } catch (Exception e) {
                logger.error("smilekay->sinaLogin->error:" + e.getMessage());
                return BaseResult.notOk(-1, LOGIN_FAIL);
            }
            logger.info("smilekay->sinaLogin->success");
            return BaseResult.ok(token, LOGIN_SUCCESS);
        }
        return BaseResult.notOk(-1, LOGIN_FAIL);
    }

}
