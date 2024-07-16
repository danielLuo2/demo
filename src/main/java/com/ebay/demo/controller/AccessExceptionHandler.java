package com.ebay.demo.controller;

import com.ebay.demo.common.BaseResponse;
import com.ebay.demo.exception.AccessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author xudong luo
 * @Date 2024/7/15
 * @Description //TODO
 */

@ControllerAdvice
@Slf4j
public class AccessExceptionHandler {
    @ExceptionHandler(AccessException.class)
    @ResponseBody
    public BaseResponse handleAccessException(AccessException e) {
        log.error(e.getMessage());
        String message = e.getMessage();
        return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

}
