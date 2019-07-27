package com.nmt.smilekay.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_video")
public class TbVideo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 3320118569631773416L;
    /**
     * 视频id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 视频名称
     */
    private String name;

    /**
     * 标题
     */
    private String title;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 标签，多个标签以符号 | 分隔
     */
    private String tag;

    /**
     * 主演人员，多个以符号 | 分隔
     */
    private String actor;

    /**
     * 编号
     */
    private String code;

    /**
     * 封面路径
     */
    private String cover;

    /**
     * 播放次数
     */
    @Column(name = "play_count")
    private Integer playCount;

    /**
     * 喜欢次数
     */
    @Column(name = "star_count")
    private Integer starCount;

    /**
     * 状态，0：正常，1：删除
     */
    private Integer status;
}