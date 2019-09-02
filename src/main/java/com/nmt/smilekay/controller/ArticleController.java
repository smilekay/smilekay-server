package com.nmt.smilekay.controller;

import com.nmt.smilekay.dto.BaseResult;
import com.nmt.smilekay.entity.TbArticle;
import com.nmt.smilekay.entity.TbUser;
import com.nmt.smilekay.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.nmt.smilekay.constant.WebConstant.SESSION_USER;
import static com.nmt.smilekay.dto.BaseResult.RESULT_FAIL;
import static com.nmt.smilekay.dto.BaseResult.RESULT_SUCCESS;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/17 19:02
 */
@RestController
@RequestMapping("article")
public class ArticleController extends BaseController<TbArticle, ArticleService> {
    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public BaseResult save(String title, String content, String introduce, HttpServletRequest request) {
        TbUser tbUser = (TbUser) request.getSession().getAttribute(SESSION_USER);
        TbArticle tbArticle = TbArticle.builder().title(title).content(content).introduce(introduce)
                .userId(tbUser.getId()).userName(tbUser.getUserName()).build();
        tbArticle.setCreateBy(tbUser.getLoginCode());
        int ret = this.getService().insertSelective(tbArticle);
        if (ret > 0) {
            return BaseResult.ok(0, RESULT_SUCCESS);
        }
        return BaseResult.notOk(-1, RESULT_FAIL);
    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public BaseResult delete(int id) {
        TbArticle tbArticle = TbArticle.builder().id(id).build();
        int ret = this.getService().delete(tbArticle);
        if (ret > 0) {
            return BaseResult.ok(0, RESULT_SUCCESS);
        }
        return BaseResult.notOk(-1, RESULT_FAIL);
    }
}
