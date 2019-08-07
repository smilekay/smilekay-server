package com.nmt.smilekay.dto;

import com.nmt.smilekay.entity.TbNews;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
@Data
public class NewsResult implements Serializable {
    private String channel;
    private int num;
    private List<TbNews> list;
}
