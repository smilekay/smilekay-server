package com.nmt.smilekay.entity;

import javax.persistence.*;

@Table(name = "tb_news")
public class TbNews {
    private Integer id;

    /**
     * 频道
     */
    private String channel;

    /**
     * 新闻标题
     */
    private String title;

    /**
     * 发布时间
     */
    private String time;

    /**
     * 信息来源
     */
    private String src;

    /**
     * 所属类别
     */
    private String category;

    /**
     * 图片地址
     */
    private String pic;

    /**
     * 链接地址
     */
    private String url;

    /**
     * 网页地址
     */
    private String weburl;

    /**
     * 新闻内容
     */
    private String content;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取频道
     *
     * @return channel - 频道
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 设置频道
     *
     * @param channel 频道
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * 获取新闻标题
     *
     * @return title - 新闻标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置新闻标题
     *
     * @param title 新闻标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取发布时间
     *
     * @return time - 发布时间
     */
    public String getTime() {
        return time;
    }

    /**
     * 设置发布时间
     *
     * @param time 发布时间
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 获取信息来源
     *
     * @return src - 信息来源
     */
    public String getSrc() {
        return src;
    }

    /**
     * 设置信息来源
     *
     * @param src 信息来源
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * 获取所属类别
     *
     * @return category - 所属类别
     */
    public String getCategory() {
        return category;
    }

    /**
     * 设置所属类别
     *
     * @param category 所属类别
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 获取图片地址
     *
     * @return pic - 图片地址
     */
    public String getPic() {
        return pic;
    }

    /**
     * 设置图片地址
     *
     * @param pic 图片地址
     */
    public void setPic(String pic) {
        this.pic = pic;
    }

    /**
     * 获取链接地址
     *
     * @return url - 链接地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置链接地址
     *
     * @param url 链接地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取网页地址
     *
     * @return weburl - 网页地址
     */
    public String getWeburl() {
        return weburl;
    }

    /**
     * 设置网页地址
     *
     * @param weburl 网页地址
     */
    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    /**
     * 获取新闻内容
     *
     * @return content - 新闻内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置新闻内容
     *
     * @param content 新闻内容
     */
    public void setContent(String content) {
        this.content = content;
    }
}