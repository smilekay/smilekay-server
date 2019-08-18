package com.nmt.smilekay.controller;

import com.nmt.smilekay.dto.BaseResult;
import com.nmt.smilekay.entity.TbArticle;
import com.nmt.smilekay.entity.TbReply;
import com.nmt.smilekay.entity.TbUser;
import com.nmt.smilekay.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.nmt.smilekay.constant.WebConstant.SESSION_USER;
import static com.nmt.smilekay.dto.BaseResult.RESULT_FAIL;
import static com.nmt.smilekay.dto.BaseResult.RESULT_SUCCESS;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/17 21:32
 */
@RestController
@RequestMapping("reply")
public class ReplyController extends BaseController<TbReply, ReplyService> {
    private final Logger logger = LoggerFactory.getLogger(ReplyController.class);

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public BaseResult save(int articleId, int parentId, String comment, HttpServletRequest request) {
        TbUser tbUser = (TbUser) request.getSession().getAttribute(SESSION_USER);
        TbReply tbReply = TbReply.builder().articleId(articleId).parentId(parentId).comment(comment).userId(tbUser.getId()).build();
        tbReply.setCreateBy(tbUser.getLoginCode());
        int ret = this.getService().insertSelective(tbReply);
        if (ret > 0) {
            return BaseResult.ok(0, RESULT_SUCCESS);
        }
        return BaseResult.notOk(-1, RESULT_FAIL);
    }

    @RequestMapping(value = "tree/{articleId}", method = RequestMethod.GET)
    public BaseResult tree(@PathVariable("articleId") int articleId) {
        TbReply tbReply = TbReply.builder().articleId(articleId).build();
        List<TbReply> tbReplyList = this.getService().select(tbReply);
        List list = this.getService().tree(tbReplyList);
        return BaseResult.ok(list);
    }
}
