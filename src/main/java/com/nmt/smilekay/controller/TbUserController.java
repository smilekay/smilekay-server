package com.nmt.smilekay.controller;

import com.nmt.smilekay.dto.BaseResult;
import com.nmt.smilekay.dto.UserBaseInfo;
import com.nmt.smilekay.entity.TbUser;
import com.nmt.smilekay.service.RedisService;
import com.nmt.smilekay.service.TbUserService;
import com.nmt.smilekay.utils.MapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Set;

import static com.nmt.smilekay.dto.BaseResult.*;

@RestController
public class TbUserController {
    private final Logger logger = LoggerFactory.getLogger(TbUserController.class);

    @Autowired
    private TbUserService tbUserService;
    @Autowired
    private RedisService redisService;

    @RequestMapping("get_user_info")
    public BaseResult getUserInfo(String token) {
        String loginCode = (String) redisService.get(token);
        if (loginCode != null) {
            String json = (String) redisService.get(loginCode);
            if (json != null) {
                UserBaseInfo userBaseInfo = new UserBaseInfo();
                try {
                    TbUser tbUser = MapperUtils.json2pojo(json, TbUser.class);
                    userBaseInfo.setUsername(tbUser.getUserName());
                    userBaseInfo.setAvatar(tbUser.getAvatar());
                    userBaseInfo.setCheck(tbUser.getIsCheck().equals("0") ? false : true);
                    userBaseInfo.setIntegral(tbUser.getIntegral());
                } catch (Exception e) {
                    logger.error("smilekay->getUserInfo->error:" + e.getMessage());
                }
                return BaseResult.ok(userBaseInfo, GET_USER_INFO_SUCCESS);
            }
        }
        return BaseResult.notOk(-1, GET_USER_INFO_FAIL);
    }

    @RequestMapping("sign")
    public BaseResult attend(String token) {
        String loginCode = (String) redisService.get(token);
        if (loginCode != null) {
            String json = (String) redisService.get(loginCode);
            if (json != null) {
                try {
                    TbUser tbUser = MapperUtils.json2pojo(json, TbUser.class);
                    tbUser.setIsCheck("1");
                    tbUser.setIntegral(tbUser.getIntegral() + 1);
                    int res = tbUserService.update(tbUser);
                    if (res > 0) {
                        //同步修改缓存中的数据
                        redisService.update(loginCode, MapperUtils.obj2json(tbUser));
                        return BaseResult.ok(0, SIGN_SUCCESS);
                    }
                } catch (Exception e) {
                    logger.error("smilekay->attend->error:" + e.getMessage());
                }
            }
        }
        return BaseResult.ok(-1, SIGN_FAIL);
    }

    /**
     * 每天晚上12点重置签到状态
     */
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void initCheck() {
        Example example = new Example(TbUser.class);
        example.createCriteria().andEqualTo("isCheck", "1");
        List<TbUser> list = tbUserService.selectByExample(example);
        Set<String> loginCodes = redisService.getLoginCodes();
        for (TbUser tbUser : list) {
            tbUser.setIsCheck("0");
            tbUserService.update(tbUser);
            if (loginCodes.contains(tbUser.getLoginCode())) {
                try {
                    redisService.update(tbUser.getLoginCode(), MapperUtils.obj2json(tbUser));
                } catch (Exception e) {
                    logger.error("smilekay->initCheck->error:" + e.getMessage());
                }
            }
        }
    }
}
