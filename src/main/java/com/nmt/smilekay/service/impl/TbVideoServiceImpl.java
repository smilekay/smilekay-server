package com.nmt.smilekay.service.impl;

import com.nmt.smilekay.entity.TbVideo;
import com.nmt.smilekay.mapper.TbVideoMapper;
import com.nmt.smilekay.service.TbVideoService;
import org.springframework.stereotype.Service;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
 */
@Service("tbVideoService")
public class TbVideoServiceImpl extends BaseServiceImpl<TbVideo, TbVideoMapper> implements TbVideoService<TbVideo> {
}
