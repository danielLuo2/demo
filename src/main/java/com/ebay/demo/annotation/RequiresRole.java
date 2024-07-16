package com.ebay.demo.annotation;

/**
 * @Author xudong luo
 * @Date 2024/7/15
 * @Description //TODO
 */
import com.ebay.demo.common.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresRole {
    Role[] value();
}
