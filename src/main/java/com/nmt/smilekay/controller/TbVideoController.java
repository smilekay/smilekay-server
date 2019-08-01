package com.nmt.smilekay.controller;

import com.nmt.smilekay.dto.BaseResult;
import com.nmt.smilekay.entity.TbVideo;
import com.nmt.smilekay.service.TbVideoService;
import com.nmt.smilekay.utils.SkPasswordEncoder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import static com.nmt.smilekay.dto.BaseResult.RESULT_FAIL;
import static com.nmt.smilekay.dto.BaseResult.RESULT_SUCCESS;


/**
 * @author mingtao.ni
 */
@RestController
@RequestMapping("video")
public class TbVideoController extends BaseController<TbVideo, TbVideoService> {
    private final Logger logger = LoggerFactory.getLogger(TbUserController.class);

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public BaseResult save(String name, String title, Integer type, String tag, String actor, String cover, String code,
                           @RequestParam(required = false) String remark, @RequestParam(required = false) String operator) {
        TbVideo tbVideo = TbVideo.builder()
                .name(name)
                .title(title)
                .type(type)
                .tag(tag)
                .actor(actor)
                .cover(cover)
                .code(code)
                .build();
        if (StringUtils.isNotBlank(remark)) {
            tbVideo.setRemarks(remark);
        }
        if (StringUtils.isNotBlank(operator)) {
            tbVideo.setCreateBy(operator);
            tbVideo.setUpdateBy(operator);
        }
        logger.info("tbVideo = " + tbVideo.toString());
        int ret = getService().insertSelective(tbVideo);
        if (ret > 0) {
            return BaseResult.ok(0, RESULT_SUCCESS);
        }
        return BaseResult.notOk(-1, RESULT_FAIL);
    }

    @RequestMapping
    public BaseResult get(String name) {
        if (StringUtils.isNotBlank(name)) {
            TbVideo example = TbVideo.builder().name(name).build();
            TbVideo tbVideo = (TbVideo) getService().selectOne(example);
            if (tbVideo != null) {
                tbVideo.setPlayCount(tbVideo.getPlayCount() + 1);
                getService().update(tbVideo);
                return BaseResult.ok(tbVideo, RESULT_SUCCESS);
            }
        }
        return BaseResult.notOk(-1, RESULT_FAIL);
    }

    @RequestMapping("name")
    public BaseResult name() {
        String name = SkPasswordEncoder.getInstance().autoGenerationCode();
        return BaseResult.ok(name);
    }
}
