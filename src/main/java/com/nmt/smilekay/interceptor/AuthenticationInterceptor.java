package com.nmt.smilekay.interceptor;

import com.nmt.smilekay.constant.WebConstant;
import com.nmt.smilekay.dto.BaseResult;
import com.nmt.smilekay.entity.TbUser;
import com.nmt.smilekay.service.RedisService;
import com.nmt.smilekay.utils.CookieUtils;
import com.nmt.smilekay.utils.HttpServletUtils;
import com.nmt.smilekay.utils.MapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author mingtao.ni
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("Interceptor url: " + request.getRequestURL());
        String token = CookieUtils.getCookieValue(request, WebConstant.SESSION_TOKEN);
        request.getSession().removeAttribute(WebConstant.SESSION_USER);
        response.setHeader(WebConstant.WEB_EXTRA_HEADER, WebConstant.WEB_REDIRECT_URL);
        response.addHeader(WebConstant.WEB_REDIRECT_URL, HttpServletUtils.getFullPath(request));
        if (StringUtils.isNotBlank(token)) {
            String loginCode = (String) redisService.get(token);
            if (StringUtils.isNotBlank(loginCode)) {
                String json = (String) redisService.get(loginCode);
                if (StringUtils.isNotBlank(json)) {
                    try {
                        TbUser tbUser = MapperUtils.json2pojo(json, TbUser.class);
                        if (tbUser != null) {
                            request.getSession().setAttribute(WebConstant.SESSION_USER, tbUser);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
