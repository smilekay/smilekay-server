package com.nmt.smilekay.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/17 22:23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyTree {
    private Integer id;
    private Integer articleId;
    private Integer userId;
    private Integer parentId;
    private String comment;
    private String level;
    private Integer status;
    private String createBy;
    private Date createDate;
    private List<ReplyTree> list;
}
