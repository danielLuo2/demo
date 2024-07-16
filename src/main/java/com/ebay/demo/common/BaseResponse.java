package com.ebay.demo.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @Author xudong luo
 * @Date 2024/7/15
 * @Description //TODO
 */
@Data
public class BaseResponse<T> {

    private int statusCode;

    private String message;

    private T data;

    public BaseResponse(HttpStatus statusCode, String message, T data) {
        this.statusCode = statusCode.value();
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(HttpStatus.OK, "Success", data);
    }

    public static <T> BaseResponse<T> success(String msg, T data) {
        return new BaseResponse<>(HttpStatus.OK, msg, data);
    }

    public static <T> BaseResponse<T> error(HttpStatus statusCode, String message) {
        return new BaseResponse<>(statusCode, message, null);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
