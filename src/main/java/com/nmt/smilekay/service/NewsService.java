package com.nmt.smilekay.service;

import com.nmt.smilekay.entity.TbNews;

import java.util.List;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
public interface NewsService {
    List<TbNews> findNews(String channel);

    void addNews(TbNews tbNews);

    List<TbNews> getNews(String channel);
}
