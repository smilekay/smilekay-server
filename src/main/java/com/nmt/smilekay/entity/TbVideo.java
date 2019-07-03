package com.nmt.smilekay.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@SuperBuilder
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_video")
public class TbVideo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -875100156788218170L;
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
}