package com.nmt.smilekay.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.nmt.smilekay.dto.ReplyTree;
import com.nmt.smilekay.entity.TbReply;
import com.nmt.smilekay.mapper.TbReplyMapper;
import com.nmt.smilekay.service.ReplyService;
import com.nmt.smilekay.utils.LevelUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/17 18:57
 */
@Service("replyService")
public class ReplyServiceImpl extends BaseServiceImpl<TbReply, TbReplyMapper> implements ReplyService<TbReply> {
    @Override
    public int insertSelective(TbReply tbReply) {
        tbReply.setLevel(LevelUtil.calculateLevel(getLevel(tbReply.getParentId()), tbReply.getParentId()));
        return super.insertSelective(tbReply);
    }

    @Override
    public List<TbReply> select(TbReply tbReply) {
        return this.getDao().select(tbReply);
    }

    @Override
    public List<ReplyTree> tree(List<TbReply> tbReplyList) {
        if (CollectionUtils.isEmpty(tbReplyList)) {
            return Lists.newArrayList();
        }
        List<ReplyTree> replyTrees = Lists.newArrayList();
        for (TbReply tbReply : tbReplyList) {
            ReplyTree replyTree = new ReplyTree();
            BeanUtils.copyProperties(tbReply, replyTree);
            replyTrees.add(replyTree);
        }
        Multimap<String, ReplyTree> levelMap = ArrayListMultimap.create();
        List<ReplyTree> rootList = Lists.newArrayList();
        for (ReplyTree t : replyTrees) {
            levelMap.put(t.getLevel(), t);
            if (LevelUtil.ROOT.equals(t.getLevel())) {
                rootList.add(t);
            }
        }
        Collections.sort(rootList, Comparator.comparing(ReplyTree::getCreateDate));
        transformTree(rootList, LevelUtil.ROOT, levelMap);
        return rootList;
    }

    private String getLevel(Integer id) {
        TbReply tbReply = this.getDao().selectByPrimaryKey(id);
        if (tbReply == null) {
            return null;
        }
        return tbReply.getLevel();
    }

    private void transformTree(List<ReplyTree> rootList, String root, Multimap<String, ReplyTree> levelMap) {
        for (int i = 0; i < rootList.size(); i++) {
            ReplyTree t = rootList.get(i);
            String nextLevel = LevelUtil.calculateLevel(root, t.getId());
            List<ReplyTree> tempList = (List<ReplyTree>) levelMap.get(nextLevel);
            if (!CollectionUtils.isEmpty(tempList)) {
                Collections.sort(tempList, Comparator.comparing(ReplyTree::getCreateDate));
                t.setList(tempList);
                transformTree(tempList, nextLevel, levelMap);
            }
        }
    }
}
