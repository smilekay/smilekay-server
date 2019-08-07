package com.nmt.smilekay.service.impl;

import com.nmt.smilekay.entity.TbUser;
import com.nmt.smilekay.mapper.TbUserMapper;
import com.nmt.smilekay.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
@Service("tbUserService")
public class TbUserServiceImpl extends BaseServiceImpl<TbUser, TbUserMapper> implements TbUserService<TbUser> {

}
