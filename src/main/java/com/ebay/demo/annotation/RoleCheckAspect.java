package com.ebay.demo.annotation;


import com.ebay.demo.bean.UserInfo;
import com.ebay.demo.common.CommonError;
import com.ebay.demo.exception.AccessException;
import com.ebay.demo.util.Base64Util;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

/**
 * @Author xudong luo
 * @Date 2024/7/15
 * @Description //TODO
 */
@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class RoleCheckAspect {

    private Gson gson;

    @Around("@annotation(requiresRole)")
    public Object checkRole(ProceedingJoinPoint joinPoint, RequiresRole requiresRole) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String roleInfo = Base64Util.decode(request.getHeader("RoleInfo"));
        UserInfo user = gson.fromJson(roleInfo, UserInfo.class);
        if (user == null) {
            throw new AccessException(CommonError.NO_ACCESS_ERROR.getMessage());
        }
        if (!Arrays.stream(requiresRole.value())
                        .anyMatch(r-> r.equals(user.getRole()))) {
            throw new AccessException(CommonError.NO_ACCESS_ERROR.getMessage());
        }

        return joinPoint.proceed();
    }
}
