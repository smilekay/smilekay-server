package com.nmt.smilekay.controller;

import com.nmt.smilekay.dto.BaseResult;
import com.nmt.smilekay.entity.TbVideo;
import com.nmt.smilekay.service.TbVideoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.nmt.smilekay.dto.BaseResult.RESULT_FAIL;
import static com.nmt.smilekay.dto.BaseResult.RESULT_SUCCESS;

@RestController
@RequestMapping("video")
public class TbVideoController extends BaseController<TbVideo, TbVideoService> {
    private final Logger logger = LoggerFactory.getLogger(TbUserController.class);

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public BaseResult save(String name, String title, int type, String tag, String actor, String cover,
                           @RequestParam(required = false) String remark,
                           @RequestParam(required = false) String operator) {
        TbVideo tbVideo = TbVideo.builder()
                .name(name)
                .title(title)
                .type(type)
                .tag(tag)
                .actor(actor)
                .cover(cover)
                .build();
        if (StringUtils.isNotBlank(remark)) {
            tbVideo.setRemarks(remark);
        }
        if (StringUtils.isNotBlank(operator)) {
            tbVideo.setCreateBy(operator);
            tbVideo.setUpdateBy(operator);
        }
        int ret = getService().insertSelective(tbVideo);
        if (ret > 0) {
            return BaseResult.ok(0, RESULT_SUCCESS);
        }
        return BaseResult.notOk(-1, RESULT_FAIL);
    }

}
