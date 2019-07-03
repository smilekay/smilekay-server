package com.nmt.smilekay.service;

import com.nmt.smilekay.entity.BaseEntity;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    int insert(T t);

    int insertSelective(T t);

    int delete(T t);

    int update(T t);

    T selectOne(T t);

    T selectOneByExample(Example example);

    List<T> getAll();

    List<T> selectByExample(Example example);
}
