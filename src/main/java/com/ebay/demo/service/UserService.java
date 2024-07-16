package com.ebay.demo.service;

import com.ebay.demo.bean.User;
import com.ebay.demo.common.BaseResponse;
import com.ebay.demo.common.CommonError;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author xudong luo
 * @Date 2024/7/15
 * @Description //TODO
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private Gson gson;

    public void addToFile(Path path, User user) {
        // add user
        String newUserLine = String.format("%d|%s", user.getUserId(), gson.toJson(user));
        // append to users.txt
//        addToFile(path, newUserLine);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            writer.write(newUserLine);
            writer.newLine();
        } catch (IOException e) {
            log.error("addUser error when updating user file", e);
            throw new RuntimeException();
        }
        log.info("User added successfully. New user: {}", user.getUserId());

    }

    public Map<Integer, User> readUsersFromFile(Path path) throws IOException {
        Map<Integer, User> userMap = new HashMap<Integer, User>();
        List<String> lines = Files.readAllLines(path);
        if (!lines.isEmpty()) {
            userMap = lines.stream()
                    .map(one -> gson.fromJson(one.split("\\|")[1], User.class))
                    .collect(Collectors.toMap(User::getUserId, v -> v));
        }
        return userMap;
    }

    public void checkFileExists(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }
}
