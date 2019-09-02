package com.nmt.smilekay.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/17 18:40
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_article")
public class TbArticle extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 7454945599110873904L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 文字内容
     */
    private String introduce;

    /**
     * 作者id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 作者名称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 图片url
     */
    private String images;

    /**
     * 是否置顶，0：不置顶，1：置顶
     */
    private Integer top;

    /**
     * 阅读数
     */
    @Column(name = "read_count")
    private Integer readCount;

    /**
     * 回复数
     */
    @Column(name = "reply_count")
    private Integer replyCount;

    /**
     * 最后回复人id
     */
    @Column(name = "reply_id")
    private Integer replyId;

    /**
     * 最后回复人
     */
    @Column(name = "reply_name")
    private String replyName;

    /**
     * 状态，0：正常，1：删除
     */
    private Integer status;
}