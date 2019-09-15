package com.nmt.smilekay.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nmt.smilekay.entity.TbArticle;
import com.nmt.smilekay.mapper.TbArticleMapper;
import com.nmt.smilekay.service.ArticleService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/17 18:55
 */
@Service("articleService")
public class ArticleServiceImpl extends BaseServiceImpl<TbArticle, TbArticleMapper> implements ArticleService<TbArticle> {
    @Override
    public PageInfo<TbArticle> page(int pageNum, int pageSize, TbArticle tbArticle) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(pageNum, pageSize);
        Example example = new Example(TbArticle.class);
        example.createCriteria().andEqualTo("top", 1);
        List<TbArticle> tbArticleList = getDao().selectByExample(example);
        example = new Example(TbArticle.class);
        example.setOrderByClause("update_date DESC");
        List<TbArticle> tbArticleList1 = getDao().selectByExample(example);
        tbArticleList.addAll(tbArticleList1);
        PageInfo<TbArticle> pageInfo = new PageInfo<>(tbArticleList);
        return pageInfo;
    }
}
