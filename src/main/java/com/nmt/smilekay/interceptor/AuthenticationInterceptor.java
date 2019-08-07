package com.nmt.smilekay.interceptor;

import com.nmt.smilekay.constant.WebConstant;
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

import static com.nmt.smilekay.constant.WebConstant.*;

/**
 * @Author: smilekay
 * @Description：
 * @Date: 2019/8/2 20:55
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
        if (StringUtils.isNotBlank(token)) {
            String loginCode = (String) redisService.get(token);
            if (StringUtils.isNotBlank(loginCode)) {
                String json = (String) redisService.get(loginCode);
                if (StringUtils.isNotBlank(json)) {
                    try {
                        TbUser tbUser = MapperUtils.json2pojo(json, TbUser.class);
                        if (tbUser != null) {
                            request.getSession().setAttribute(WebConstant.SESSION_USER, tbUser);
                            return true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        response.setHeader(HEADER_ALLOW_ORIGIN, ALLOW_ORIGIN);
        response.setHeader(HEADER_ALLOW_CREDENTIALS, ALLOW_CREDENTIALS);
        response.setHeader(HEADER_ALLOW_METHODS, ALLOW_METHODS);
        response.setHeader(HEADER_MAX_AGE, MAX_AGE);
        response.setHeader(HEADER_EXTRA_HEADER, HEADER_ACTION);
        response.addHeader(HEADER_ACTION, WEB_NEED_LOGIN);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
