package com.nmt.smilekay.service.impl;

import com.nmt.smilekay.dto.QQUserInfo;
import com.nmt.smilekay.dto.SinaUserInfo;
import com.nmt.smilekay.utils.SkPasswordEncoder;
import com.nmt.smilekay.entity.TbUser;
import com.nmt.smilekay.service.LoginService;
import com.nmt.smilekay.service.RedisService;
import com.nmt.smilekay.service.TbUserService;
import com.nmt.smilekay.utils.GeneralUtils;
import com.nmt.smilekay.utils.MapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
    private final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private RedisService redisService;
    @Autowired
    private TbUserService tbUserService;

    /**
     * 登陆
     *
     * @param loginCode      账号
     * @param password       密码
     * @param isThirdPaLogin 是否为第三方登陆
     * @return
     */
    @Override
    public TbUser login(String loginCode, String password, boolean isThirdPaLogin) {
        logger.info("smilekay->login->loginCode:" + loginCode);
        TbUser tbUser = null;
        String json = (String) redisService.get(loginCode);
        if (json == null) {
            Example example = new Example(TbUser.class);
            example.createCriteria().andEqualTo("loginCode", loginCode);
            tbUser = (TbUser) tbUserService.selectOneByExample(example);
            String pwd = password;
            if (!isThirdPaLogin) {
                pwd = SkPasswordEncoder.getInstance().encode(password);
            }
            if (tbUser != null && pwd.equals(tbUser.getPassword())) {
                try {
                    redisService.put(loginCode, MapperUtils.obj2json(tbUser), 60 * 60 * 24);
                } catch (Exception e) {
                    logger.error("smilekay->login->error:" + e.getMessage());
                }
                return tbUser;
            } else {
                return null;
            }
        } else {
            try {
                tbUser = MapperUtils.json2pojo(json, TbUser.class);
            } catch (Exception e) {
                logger.error("smilekay->login->error:" + e.getMessage());
            }
        }
        return tbUser;
    }

    @Override
    public void logout() {

    }

    /**
     * 使用表单信息注册
     *
     * @param tbUser
     * @return
     */
    @Override
    public int register(TbUser tbUser) {
        Example example = new Example(TbUser.class);
        example.createCriteria().andEqualTo("loginCode", tbUser.getLoginCode());
        TbUser user = (TbUser) tbUserService.selectOneByExample(example);
        if (user == null) {
            int ret = tbUserService.insertSelective(tbUser);
            if (ret > 0) {
                return 0;
            }
        }
        return 1;
    }


    /**
     * 使用QQ信息注册
     *
     * @param userInfo
     * @param openId   获取到的accessToken
     * @return
     */
    @Override
    public TbUser register(QQUserInfo userInfo, String openId) {
        TbUser tbUser = new TbUser();
        String loginCode = GeneralUtils.getRandomName();
        String pwd = loginCode + "p";
        tbUser.setLoginCode(loginCode);
        tbUser.setPassword(SkPasswordEncoder.getInstance().encode(pwd));
        tbUser.setUserName(userInfo.getNickname());
        tbUser.setQqOpenid(openId);
        tbUser.setAvatar(userInfo.getFigureurl());
        tbUser.setSex(userInfo.getGender());
        int ret = tbUserService.insertSelective(tbUser);
        if (ret > 0) {
            return tbUser;
        }
        return null;
    }

    /**
     * 使用微博信息注册
     *
     * @param userInfo
     * @param openId   获取到的uid
     * @return
     */
    @Override
    public TbUser register(SinaUserInfo userInfo, String openId) {
        TbUser tbUser = new TbUser();
        String loginCode = GeneralUtils.getRandomName();
        String pwd = loginCode + "p";
        tbUser.setLoginCode(loginCode);
        tbUser.setPassword(SkPasswordEncoder.getInstance().encode(pwd));
        tbUser.setUserName(userInfo.getName());
        tbUser.setSinaOpenid(openId);
        tbUser.setAvatar(userInfo.getProfile_image_url());
        tbUser.setSex(userInfo.getGender());
        int ret = tbUserService.insertSelective(tbUser);
        if (ret > 0) {
            return tbUser;
        }
        return null;
    }

}
