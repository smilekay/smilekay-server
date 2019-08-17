package com.nmt.smilekay.service.impl;

import com.nmt.smilekay.dto.NewsResponse;
import com.nmt.smilekay.entity.TbNews;
import com.nmt.smilekay.mapper.TbNewsMapper;
import com.nmt.smilekay.service.NewsService;
import com.nmt.smilekay.utils.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
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
    public List<TbNews> getNews(String channel) {
        TbNews build = TbNews.builder().channel(channel).build();
        List<TbNews> tbNewsList = tbNewsMapper.select(build);
        if (tbNewsList != null && tbNewsList.size() > 0) {
            try {
                String time = tbNewsList.get(0).getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(time);
                String var = sdf.format(date);
                String now = sdf.format(new Date());
                if (!var.equals(now)) {
                    List<TbNews> newsFromInternet = getNewsFromInternet(channel);
                    if (newsFromInternet != null) {
                        tbNewsMapper.delete(build);
                        return newsFromInternet;
                    }
                }
                return tbNewsList;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            return getNewsFromInternet(channel);
        }
        return null;
    }

    private List<TbNews> getNewsFromInternet(String channel) {
        String path = url + "?channel={channel}&start=0&num=10&appkey={appkey}";
        Map<String, String> map = new HashMap<>(2);
        map.put("channel", channel);
        map.put("appkey", appkey);
        try {
            String response = restTemplate.getForObject(path, String.class, map);
            NewsResponse newsResponse = MapperUtils.json2pojo(response, NewsResponse.class);
            if (newsResponse != null && newsResponse.getStatus() == 0) {
                return newsResponse.getResult().getList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
