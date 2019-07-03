package com.nmt.smilekay.controller;

import com.github.pagehelper.PageInfo;
import com.nmt.smilekay.dto.BaseResult;
import com.nmt.smilekay.entity.BaseEntity;
import com.nmt.smilekay.service.BaseService;
import com.nmt.smilekay.utils.MapperUtils;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseController<T extends BaseEntity, S extends BaseService> {

    @Getter
    @Autowired
    private S service;

    @ResponseBody
    @RequestMapping(value = "page/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public BaseResult page(@PathVariable int pageNum, @PathVariable int pageSize,
                           @RequestParam(required = false) String tJson) throws Exception {
        T t = null;
        if (StringUtils.isNotBlank(tJson)) {
            t = MapperUtils.json2pojo(tJson, (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        }
        PageInfo pageInfo = service.page(pageNum, pageSize, t);
        List<T> list = pageInfo.getList();
        BaseResult.Cursor cursor = new BaseResult.Cursor();
        cursor.setTotal(new Long(pageInfo.getTotal()).intValue());
        cursor.setOffset(pageInfo.getPageNum());
        cursor.setLimit(pageInfo.getPageSize());
        return BaseResult.ok(list, cursor);
    }
}
