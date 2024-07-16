package com.ebay.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xudong luo
 * @Date 2024/7/15
 * @Description //TODO
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccessException extends RuntimeException {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

}
