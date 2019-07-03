package com.nmt.smilekay.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
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
}