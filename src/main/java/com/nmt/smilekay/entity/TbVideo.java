package com.nmt.smilekay.entity;

import javax.persistence.*;
import java.io.Serializable;

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
     * 获取视频id
     *
     * @return id - 视频id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置视频id
     *
     * @param id 视频id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取视频名称
     *
     * @return name - 视频名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置视频名称
     *
     * @param name 视频名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取类型
     *
     * @return type - 类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取标签，多个标签以符号 | 分隔
     *
     * @return tag - 标签，多个标签以符号 | 分隔
     */
    public String getTag() {
        return tag;
    }

    /**
     * 设置标签，多个标签以符号 | 分隔
     *
     * @param tag 标签，多个标签以符号 | 分隔
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 获取主演人员，多个以符号 | 分隔
     *
     * @return actor - 主演人员，多个以符号 | 分隔
     */
    public String getActor() {
        return actor;
    }

    /**
     * 设置主演人员，多个以符号 | 分隔
     *
     * @param actor 主演人员，多个以符号 | 分隔
     */
    public void setActor(String actor) {
        this.actor = actor;
    }

    /**
     * 获取播放次数
     *
     * @return play_count - 播放次数
     */
    public Integer getPlayCount() {
        return playCount;
    }

    /**
     * 设置播放次数
     *
     * @param playCount 播放次数
     */
    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    /**
     * 获取喜欢次数
     *
     * @return star_count - 喜欢次数
     */
    public Integer getStarCount() {
        return starCount;
    }

    /**
     * 设置喜欢次数
     *
     * @param starCount 喜欢次数
     */
    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }
}