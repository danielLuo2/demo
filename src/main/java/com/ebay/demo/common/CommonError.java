package com.ebay.demo.common;

/**
 * @Author xudong luo
 * @Date 2024/7/15
 * @Description //TODO
 */
public enum CommonError {

    UNKOWN_ERROR("internal server error"),
    ALREADY_EXIST_ERROR("already exist"),
    NO_SUCH_USER_ERROR("no such user"),
    NO_ACCESS_ERROR("no access permission");


    private String message;

    CommonError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
