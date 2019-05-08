package com.nmt.smilekay.service.impl;

import com.nmt.smilekay.dto.NewsResponse;
import com.nmt.smilekay.entity.TbNews;
import com.nmt.smilekay.mapper.TbNewsMapper;
import com.nmt.smilekay.service.NewsService;
import com.nmt.smilekay.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("newsService")
public class NewsServiceImpl implements NewsService {
    @Autowired
    private TbNewsMapper tbNewsMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${smilekay.jisu.url}")
    private String url;
    @Value("${smilekay.jisu.appkey}")
    private String appkey;

    @Override
    public List<TbNews> findNews(String channel) {
        Example example = new Example(TbNews.class);
        example.createCriteria().andEqualTo("channel", channel);
        List<TbNews> tbNews = tbNewsMapper.selectByExample(example);
        return tbNews;
    }

    @Override
    public void addNews(TbNews tbNews) {
        Example example = new Example(TbNews.class);
        example.createCriteria().andEqualTo("title", tbNews.getTitle());
        TbNews tbNews1 = tbNewsMapper.selectOneByExample(example);
        if (tbNews1 == null) {
            tbNewsMapper.insert(tbNews);
        }
    }

    @Override
    public NewsResponse getNews(String channel) {
        String path = url + "?channel={channel}&start=0&num=10&appkey={appkey}";
        Map<String, String> map = new HashMap<>();
        map.put("channel", channel);
        map.put("appkey", appkey);
        String response = restTemplate.getForObject(path, String.class, map);
        try {
            return MapperUtils.json2pojo(response, NewsResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解决中文乱码问题
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> httpMessageConverter : list) {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(Charset.forName("UTF-8"));
            }
        }
        return restTemplate;
    }
}
