package com.nmt.smilekay.service;

import com.nmt.smilekay.dto.NewsResponse;
import com.nmt.smilekay.entity.TbNews;

import java.util.List;

public interface NewsService {
    List<TbNews> findNews(String channel);

    void addNews(TbNews tbNews);

    NewsResponse getNews(String channel);
}
