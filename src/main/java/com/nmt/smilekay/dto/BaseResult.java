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
    private Cursor cursor;

    public static BaseResult ok(Object data, Cursor cursor) {
        return createResult(RESULT_OK, data, cursor, SUCCESS);
    }

    public static BaseResult ok(Object data, String success) {
        return createResult(RESULT_OK, data, null, success);
    }

    public static BaseResult ok(Object data) {
        return createResult(RESULT_OK, data, null, SUCCESS);
    }

    public static BaseResult notOk() {
        return createResult(RESULT_NOT_OK, null, null, "");
    }

    public static BaseResult notOk(Object data, String error) {
        return createResult(RESULT_NOT_OK, data, null, error);
    }

    public static BaseResult notOk(Object data) {
        return createResult(RESULT_NOT_OK, data, null, "");
    }


    /**
     * @param result
     * @param data
     * @param message
     * @return
     */
    private static BaseResult createResult(String result, Object data, Cursor cursor, String message) {
        BaseResult baseResult = new BaseResult();
        baseResult.setResult(result);
        baseResult.setData(data);
        baseResult.setCursor(cursor);
        baseResult.setMessage(message);
        return baseResult;
    }

    @Data
    public static class Cursor {
        private int total;
        private int offset;
        private int limit;
    }

    public static final String RESULT_SUCCESS = "操作成功";

    public static final String RESULT_FAIL = "操作失败";

    public static final String USER_NOT_EXIST = "用户不存在";

    public static final String LOGIN_SUCCESS = "登录成功";

    public static final String LOGIN_FAIL = "登录失败";

    public static final String ALREADY_REGISTER = "用户已注册";

    public static final String REGISTER_SUCCESS = "注册成功";

    public static final String REGISTER_FAIL = "注册失败";

    public static final String LOGOUT_SUCCESS = "注销成功";

    public static final String LOGOUT_FAIL = "注销失败";

    public static final String ALREADY_LOGIN = "用户已登录";

    public static final String NOT_LOGIN = "用户未登录";

    public static final String GET_USER_INFO_SUCCESS = "获取用户信息成功";

    public static final String GET_USER_INFO_FAIL = "获取用户信息失败";

    public static final String SIGN_SUCCESS = "签到成功";

    public static final String SIGN_FAIL = "签到失败";
}
