package com.nmt.smilekay.controller;

import com.nmt.smilekay.constant.WebConstant;
import com.nmt.smilekay.dto.*;
import com.nmt.smilekay.entity.TbUser;
import com.nmt.smilekay.service.LoginService;
import com.nmt.smilekay.service.RedisService;
import com.nmt.smilekay.service.TbUserService;
import com.nmt.smilekay.service.ThirdPartyService;
import com.nmt.smilekay.utils.CookieUtils;
import com.nmt.smilekay.utils.SkPasswordEncoder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.nmt.smilekay.dto.BaseResult.*;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
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
    public BaseResult login(String loginCode, String password, @RequestParam(required = false) String portal,
                            HttpServletRequest request, HttpServletResponse response) {
        TbUser tbUser = loginService.login(loginCode, password, false);
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        String token = UUID.randomUUID().toString();
        if (tbUser == null) {
            return BaseResult.notOk(-1, USER_NOT_EXIST);
        } else {
            try {
                BeanUtils.copyProperties(tbUser, userBaseInfo);
                userBaseInfo.setCheck(tbUser.getIsCheck().equals("0") ? false : true);
                redisService.put(token, loginCode, 60 * 60 * 24);
                CookieUtils.setCookie(request, response, WebConstant.SESSION_TOKEN, token, 60 * 60 * 24);
                if (StringUtils.isNotBlank(portal)) {
                    response.addHeader(WebConstant.HEADER_REDIRECT_URL, portal);
                }
            } catch (Exception e) {
                logger.error("smilekay->login->error:" + e.getMessage());
                return BaseResult.notOk(-1, LOGIN_FAIL);
            }
        }
        logger.info("smilekay->login->success");
        return BaseResult.ok(userBaseInfo, LOGIN_SUCCESS);
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
                return BaseResult.notOk(-1, ALREADY_REGISTER);
            }
        } catch (Exception e) {
            logger.error("smilekay->register->error:" + e.getMessage());
            return BaseResult.notOk(-1, REGISTER_FAIL);
        }
        return BaseResult.ok(0, REGISTER_SUCCESS);
    }

    @RequestMapping("logout")
    public BaseResult logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            String token = CookieUtils.getCookieValue(request, WebConstant.SESSION_TOKEN);
            CookieUtils.deleteCookie(request, response, WebConstant.SESSION_TOKEN);
            String loginCode = (String) redisService.get(token);
            redisService.delete(token);
            if (StringUtils.isNotBlank(loginCode)) {
                redisService.delete(loginCode);
            }
            return BaseResult.ok(0, LOGOUT_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResult.notOk(-1, LOGOUT_FAIL);
    }

    @RequestMapping("login/qq")
    public BaseResult qqLogin(String accessToken, HttpServletRequest request, HttpServletResponse response) {
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
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        try {
            BeanUtils.copyProperties(tbUser, userBaseInfo);
            userBaseInfo.setCheck(tbUser.getIsCheck().equals("0") ? false : true);
            redisService.put(token, tbUser.getLoginCode(), 60 * 60 * 24);
            CookieUtils.setCookie(request, response, WebConstant.SESSION_TOKEN, token, 60 * 60 * 24);
        } catch (Exception e) {
            logger.error("smilekay->qqLogin->error:" + e.getMessage());
            return BaseResult.notOk(-1, LOGIN_FAIL);
        }
        logger.info("smilekay->qqLogin->success");
        return BaseResult.ok(userBaseInfo, LOGIN_SUCCESS);
    }

    @RequestMapping("login/sina")
    public BaseResult sinaLogin(String code, HttpServletRequest request, HttpServletResponse response) {
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
            UserBaseInfo userBaseInfo = new UserBaseInfo();
            try {
                BeanUtils.copyProperties(tbUser, userBaseInfo);
                userBaseInfo.setCheck(tbUser.getIsCheck().equals("0") ? false : true);
                redisService.put(token, tbUser.getLoginCode(), 60 * 60 * 24);
                CookieUtils.setCookie(request, response, WebConstant.SESSION_TOKEN, token, 60 * 60 * 24);
            } catch (Exception e) {
                logger.error("smilekay->sinaLogin->error:" + e.getMessage());
                return BaseResult.notOk(-1, LOGIN_FAIL);
            }
            logger.info("smilekay->sinaLogin->success");
            return BaseResult.ok(userBaseInfo, LOGIN_SUCCESS);
        }
        return BaseResult.notOk(-1, LOGIN_FAIL);
    }

}
