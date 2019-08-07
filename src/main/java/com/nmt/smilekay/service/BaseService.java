package com.nmt.smilekay.service;

import com.github.pagehelper.PageInfo;
import com.nmt.smilekay.entity.BaseEntity;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
public interface BaseService<T extends BaseEntity> {
    int insert(T t);

    int insertSelective(T t);

    int delete(T t);

    int update(T t);

    T selectOne(T t);

    T selectOneByExample(Example example);

    List<T> getAll();

    List<T> selectByExample(Example example);

    PageInfo<T> page(int pageNum, int pageSize, T t);
}
