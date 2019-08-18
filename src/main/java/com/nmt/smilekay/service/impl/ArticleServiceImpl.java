package com.nmt.smilekay.service.impl;

import com.nmt.smilekay.entity.TbArticle;
import com.nmt.smilekay.mapper.TbArticleMapper;
import com.nmt.smilekay.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/17 18:55
 */
@Service("articleService")
public class ArticleServiceImpl extends BaseServiceImpl<TbArticle, TbArticleMapper> implements ArticleService<TbArticle> {

}
