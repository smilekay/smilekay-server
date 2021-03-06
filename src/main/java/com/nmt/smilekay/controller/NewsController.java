package com.nmt.smilekay.controller;

import com.nmt.smilekay.dto.BaseResult;
import com.nmt.smilekay.dto.NewsResponse;
import com.nmt.smilekay.entity.TbNews;
import com.nmt.smilekay.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
@RestController
@RequestMapping("news")
public class NewsController {
    private final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public BaseResult getNews(String channel) {
        logger.info("smilekay->news->get:" + channel);
        List<TbNews> tbNewsList = newsService.getNews(channel);
        if (tbNewsList != null) {
            for (TbNews tbNews : tbNewsList) {
                tbNews.setChannel(channel);
                newsService.addNews(tbNews);
            }
            return BaseResult.ok(tbNewsList);
        }
        return BaseResult.notOk();
    }
}
