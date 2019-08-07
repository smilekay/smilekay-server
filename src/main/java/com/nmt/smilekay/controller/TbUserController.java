package com.nmt.smilekay.controller;

import com.nmt.smilekay.constant.WebConstant;
import com.nmt.smilekay.dto.BaseResult;
import com.nmt.smilekay.dto.UserBaseInfo;
import com.nmt.smilekay.entity.TbUser;
import com.nmt.smilekay.service.RedisService;
import com.nmt.smilekay.service.TbUserService;
import com.nmt.smilekay.utils.CookieUtils;
import com.nmt.smilekay.utils.MapperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

import static com.nmt.smilekay.dto.BaseResult.*;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
@RestController
@RequestMapping("user")
public class TbUserController extends BaseController<TbUser, TbUserService> {
    private final Logger logger = LoggerFactory.getLogger(TbUserController.class);

    @Autowired
    private RedisService redisService;

    @RequestMapping("get_user_info")
    public BaseResult getUserInfo(HttpServletRequest request) {
        String token = CookieUtils.getCookieValue(request, WebConstant.SESSION_TOKEN);
        String loginCode = (String) redisService.get(token);
        if (loginCode != null) {
            String json = (String) redisService.get(loginCode);
            if (json != null) {
                UserBaseInfo userBaseInfo = new UserBaseInfo();
                try {
                    TbUser tbUser = MapperUtils.json2pojo(json, TbUser.class);
                    userBaseInfo.setUserName(tbUser.getUserName());
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
    public BaseResult attend(HttpServletRequest request) {
        String token = CookieUtils.getCookieValue(request, WebConstant.SESSION_TOKEN);
        String loginCode = (String) redisService.get(token);
        if (loginCode != null) {
            String json = (String) redisService.get(loginCode);
            if (json != null) {
                try {
                    TbUser tbUser = MapperUtils.json2pojo(json, TbUser.class);
                    if ("0".equals(tbUser.getIsCheck())){
                        tbUser.setIsCheck("1");
                        tbUser.setIntegral(tbUser.getIntegral() + 1);
                        int res = getService().update(tbUser);
                        if (res > 0) {
                            //同步修改缓存中的数据
                            redisService.update(loginCode, MapperUtils.obj2json(tbUser));
                            return BaseResult.ok(0, SIGN_SUCCESS);
                        }
                    }
                } catch (Exception e) {
                    logger.error("smilekay->attend->error:" + e.getMessage());
                }
            }
        }
        return BaseResult.notOk(-1, SIGN_FAIL);
    }

    /**
     * 每天晚上12点重置签到状态
     */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 0 0 * * ?")
    public void initCheck() {
        Example example = new Example(TbUser.class);
        example.createCriteria().andEqualTo("isCheck", "1");
        List<TbUser> list = getService().selectByExample(example);
        Set<String> loginCodes = redisService.getLoginCodes();
        for (TbUser tbUser : list) {
            tbUser.setIsCheck("0");
            getService().update(tbUser);
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
