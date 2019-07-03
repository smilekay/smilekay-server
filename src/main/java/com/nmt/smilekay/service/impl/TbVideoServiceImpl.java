package com.nmt.smilekay.service.impl;

import com.nmt.smilekay.entity.TbVideo;
import com.nmt.smilekay.mapper.TbVideoMapper;
import com.nmt.smilekay.service.TbVideoService;
import org.springframework.stereotype.Service;

@Service("tbVideoService")
public class TbVideoServiceImpl extends BaseServiceImpl<TbVideo, TbVideoMapper> implements TbVideoService<TbVideo> {
}
