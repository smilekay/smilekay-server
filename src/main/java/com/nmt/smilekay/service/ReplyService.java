package com.nmt.smilekay.service;

import com.nmt.smilekay.dto.ReplyTree;
import com.nmt.smilekay.entity.BaseEntity;
import com.nmt.smilekay.entity.TbReply;

import java.util.List;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/17 18:55
 */
public interface ReplyService<T extends BaseEntity> extends BaseService<T> {
    /**
     * 查询所有符合条件的回复
     *
     * @param t
     * @return
     */
    List<T> select(T t);

    /**
     * 树形输出回复列表
     *
     * @param tbReplyList
     * @return
     */
    List<ReplyTree> tree(List<TbReply> tbReplyList);
}
