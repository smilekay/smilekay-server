package com.nmt.smilekay.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/17 18:44
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_reply")
public class TbReply extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1556615240650908631L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 回复的文章id
     */
    @Column(name = "article_id")
    private Integer articleId;

    /**
     * 回复者id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 父级id
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 回复内容
     */
    private String comment;

    /**
     * 层级
     */
    private String level;

    /**
     * 状态，0：显示，1：不显示
     */
    private Integer status;
}