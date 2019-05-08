package com.nmt.smilekay.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResult implements Serializable {
    private static final String RESULT_OK = "ok";
    private static final String RESULT_NOT_OK = "not_ok";
    private static final String SUCCESS = "成功操作";

    private String result;
    private Object data;
    private String message;


    public static BaseResult ok() {
        return createResult(RESULT_OK, null, SUCCESS);
    }

    public static BaseResult ok(Object data, String success) {
        return createResult(RESULT_OK, null, success);
    }

    public static BaseResult ok(Object data) {
        return createResult(RESULT_OK, data, SUCCESS);
    }

    public static BaseResult notOk() {
        return createResult(RESULT_NOT_OK, null, "");
    }

    public static BaseResult notOk(Object data, String error) {
        return createResult(RESULT_NOT_OK, data, error);
    }

    public static BaseResult notOk(Object data) {
        return createResult(RESULT_NOT_OK, data, "");
    }


    /**
     * @param result
     * @param data
     * @param message
     * @return
     */
    private static BaseResult createResult(String result, Object data, String message) {
        BaseResult baseResult = new BaseResult();
        baseResult.setResult(result);
        baseResult.setData(data);
        baseResult.setMessage(message);
        return baseResult;
    }

}
