package com.nmt.smilekay.utils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
