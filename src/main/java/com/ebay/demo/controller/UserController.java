package com.ebay.demo.controller;

import com.ebay.demo.annotation.RequiresRole;
import com.ebay.demo.bean.User;
import com.ebay.demo.bean.UserInfo;
import com.ebay.demo.common.BaseResponse;
import com.ebay.demo.common.CommonError;
import com.ebay.demo.service.UserService;
import com.ebay.demo.util.Base64Util;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;



import static com.ebay.demo.common.Role.USER;

/**
 * @Author xudong luo
 * @Date 2024/7/15
 * @Description //TODO
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private Gson gson;

    @Value("${user-file-path}")
    private String userFilePath;

    @Autowired
    private UserService userService;

    public UserController(Gson gson) {
        this.gson = gson;
    }

    @GetMapping("/{resource}")
    @RequiresRole({USER})
    public BaseResponse checkResource(@PathVariable("resource") String resource, HttpServletRequest request) {
        Map<Integer, User> userMap = new HashMap<Integer, User>();
        String roleInfo = Base64Util.decode(request.getHeader("RoleInfo"));
        UserInfo userInfo = gson.fromJson(roleInfo, UserInfo.class);
        int userId = userInfo.getUserId();
        Path path = Paths.get(userFilePath);
        try {
            if (Files.exists(path)) {
                userMap = userService.readUsersFromFile(path);
                if (userMap.containsKey(userId)) {
                    if (userMap.get(userId).getEndPoint().contains(resource)) {
                        return BaseResponse.success("success", null);
                    } else {
                        return BaseResponse.success("failure", null);
                    }
                } else {
                    return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, CommonError.NO_SUCH_USER_ERROR.getMessage());
                }
            } else {
                return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, CommonError.NO_SUCH_USER_ERROR.getMessage());
            }
        } catch (IOException e) {
            log.error("addUser error when reading user file", e);
            return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, CommonError.UNKOWN_ERROR.getMessage());
        }
    }
}
