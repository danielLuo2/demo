package com.ebay.demo.controller;

import com.ebay.demo.annotation.RequiresRole;
import com.ebay.demo.bean.User;
import com.ebay.demo.common.BaseResponse;
import com.ebay.demo.common.CommonError;
import com.ebay.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


import static com.ebay.demo.common.Role.ADMIN;

/**
 * @Author xudong luo
 * @Date 2024/7/15
 * @Description //TODO
 */
@RestController
@RequestMapping("/admin")
@Slf4j
//@AllArgsConstructor
public class AdminController {

    @Value("${user-file-path}")
    private String userFilePath;

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    @RequiresRole({ADMIN})
    public BaseResponse addUser(@Valid @RequestBody User user) {
        Map<Integer, User> userMap = new HashMap<Integer, User>();
        Path path = Paths.get(userFilePath);
        try {
            synchronized (this) {
                userService.checkFileExists(path);
                userMap = userService.readUsersFromFile(path);
                // check user if already exist
                if (!userMap.containsKey(user.getUserId())) {
                    userService.addToFile(path, user);
                    return BaseResponse.success(HttpStatus.OK);
                } else {
                    log.info("User with ID {} already exists.", user.getUserId());
                    return BaseResponse.success(CommonError.ALREADY_EXIST_ERROR.getMessage(), null);
                }
            }
        } catch (IOException e) {
            log.error("addUser error when reading user file. {}", e.getMessage());
            return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, CommonError.UNKOWN_ERROR.getMessage());
        } catch (RuntimeException e) {
            log.error("addUser error when updating user file. {}", e.getMessage());
            return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, CommonError.UNKOWN_ERROR.getMessage());
        }


    }
}
