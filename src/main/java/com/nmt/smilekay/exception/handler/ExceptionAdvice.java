package com.nmt.smilekay.exception.handler;

import com.nmt.smilekay.dto.BaseResult;
import com.nmt.smilekay.exception.ParamException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    @ResponseBody
    public BaseResult exceptionHandler(HttpServletRequest httpServletRequest, Exception e) {
        String url = httpServletRequest.getRequestURL().toString();
        BaseResult baseResult;
        if (e instanceof ParamException) {
            baseResult = BaseResult.notOk(-1, "Param Error " + "URL = " + url + e.getMessage());
        } else {
            baseResult = BaseResult.notOk(-1, "URL = " + url + e.getMessage());
        }
        return baseResult;
    }
}
