package com.nmt.smilekay.service.impl;

import com.nmt.smilekay.entity.TbUser;
import com.nmt.smilekay.mapper.TbUserMapper;
import com.nmt.smilekay.service.LoginService;
import com.nmt.smilekay.service.RedisService;
import com.nmt.smilekay.utils.MapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    private final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private RedisService redisService;
    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public TbUser login(String loginCode, String password) {
        logger.info("smilekay->login->loginCode:" + loginCode);
        TbUser tbUser = null;
        String json = (String) redisService.get(loginCode);
        if (json == null) {
            Example example = new Example(TbUser.class);
            example.createCriteria().andEqualTo("loginCode", loginCode);
            tbUser = tbUserMapper.selectOneByExample(example);
            String pwd = DigestUtils.md5DigestAsHex(password.getBytes());
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

    @Override
    public int register(TbUser tbUser) {
        Example example = new Example(TbUser.class);
        example.createCriteria().andEqualTo("loginCode", tbUser.getLoginCode());
        TbUser user = tbUserMapper.selectOneByExample(example);
        if (user == null) {
            tbUserMapper.insert(tbUser);
            return 0;
        }
        return 1;
    }
}
