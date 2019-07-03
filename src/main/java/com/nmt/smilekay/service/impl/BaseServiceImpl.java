package com.nmt.smilekay.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nmt.smilekay.entity.BaseEntity;
import com.nmt.smilekay.service.BaseService;
import com.nmt.smilekay.utils.MyMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

public class BaseServiceImpl<T extends BaseEntity, D extends MyMapper<T>> implements BaseService<T> {
    @Getter
    @Autowired
    private D dao;

    @Override
    public int insert(T t) {
        return dao.insert(t);
    }

    @Override
    public int insertSelective(T t) {
        return dao.insertSelective(t);
    }

    @Override
    public int delete(T t) {
        return dao.delete(t);
    }

    @Override
    public int update(T t) {
        return dao.updateByPrimaryKey(t);
    }

    @Override
    public T selectOne(T t) {
        return dao.selectOne(t);
    }

    @Override
    public T selectOneByExample(Example example) {
        return dao.selectOneByExample(example);
    }

    @Override
    public List<T> getAll() {
        return dao.selectAll();
    }

    @Override
    public List<T> selectByExample(Example example) {
        return dao.selectByExample(example);
    }

    @Override
    public PageInfo<T> page(int pageNum, int pageSize, T t) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(pageNum,pageSize);
        PageInfo<T> pageInfo = new PageInfo<>(dao.select(t));
        return pageInfo;
    }

}
