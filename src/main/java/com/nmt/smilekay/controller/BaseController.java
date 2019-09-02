package com.nmt.smilekay.controller;

import com.github.pagehelper.PageInfo;
import com.nmt.smilekay.constant.WebConstant;
import com.nmt.smilekay.dto.BaseResult;
import com.nmt.smilekay.entity.BaseEntity;
import com.nmt.smilekay.entity.TbUser;
import com.nmt.smilekay.service.BaseService;
import com.nmt.smilekay.utils.MapperUtils;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
public class BaseController<T extends BaseEntity, S extends BaseService> {

    @Getter
    @Autowired
    private S service;

    @ResponseBody
    @RequestMapping(value = "page/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public BaseResult page(@PathVariable int pageNum, @PathVariable int pageSize,
                           @RequestParam(required = false) String tJson, HttpServletRequest request) throws Exception {
//        if (request.getSession().getAttribute(WebConstant.SESSION_USER) == null) {
//            return BaseResult.notOk(1, BaseResult.NOT_LOGIN);
//        }
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
