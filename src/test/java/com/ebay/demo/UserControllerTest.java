package com.ebay.demo;

import com.ebay.demo.bean.User;
import com.ebay.demo.controller.UserController;
import com.ebay.demo.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author xudong luo
 * @Date 2024/7/16
 * @Description //TODO
 */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private UserController userController;

    @MockBean
    private UserService userService;

    private static final String ROLE_INFO = "eyJ1c2VySWQiOjEwODYsImFjY291bnROYW1lIjoiTEREIiwicm9sZSI6IlVTRVIifQ==";
    private static final String CHECK_RESOURCE_PARAM = "resource B";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testCheckResource_WhenUserDoesHaveResource_ShouldReturnSuccess() throws Exception {
        String userFilePath = "testUsers.txt";
        Path path = Paths.get(userFilePath);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        Map<Integer, User> userMap = new HashMap<Integer, User>();
        User user = new User(1086, List.of("resource A", "resource B", "resource C"));
        userMap.put(1086, user);
        ReflectionTestUtils.setField(userController, "gson", new Gson());
        ReflectionTestUtils.setField(userController, "userFilePath", userFilePath);

        when(userService.readUsersFromFile(path)).thenReturn(userMap);

        this.mvc.perform(get("/user/{resource}", CHECK_RESOURCE_PARAM)
                        .header("RoleInfo", ROLE_INFO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"statusCode\": 200," +
                        "\"message\": \"success\"," +
                        "\"data\": null" +
                        "}"));
    }

    @Test
    public void testCheckResource_WhenUserDoesNotHaveResource_ShouldReturnFailure() throws Exception {
        String userFilePath = "testUsers.txt";
        Path path = Paths.get(userFilePath);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        Map<Integer, User> userMap = new HashMap<Integer, User>();
        User user = new User(1086, List.of("resource A", "resource B", "resource C"));
        userMap.put(1086, user);
        ReflectionTestUtils.setField(userController, "gson", new Gson());
        ReflectionTestUtils.setField(userController, "userFilePath", userFilePath);

        when(userService.readUsersFromFile(path)).thenReturn(userMap);

        this.mvc.perform(get("/user/{resource}", "resource D")
                        .header("RoleInfo", ROLE_INFO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"statusCode\": 200," +
                        "\"message\": \"failure\"," +
                        "\"data\": null" +
                        "}"));
    }

    @Test
    public void testCheckResource_WhenUserNotExist_ShouldReturnFailure() throws Exception {
        String userFilePath = "testUsers.txt";
        Path path = Paths.get(userFilePath);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        Map<Integer, User> userMap = new HashMap<Integer, User>();
        ReflectionTestUtils.setField(userController, "gson", new Gson());
        ReflectionTestUtils.setField(userController, "userFilePath", userFilePath);

        when(userService.readUsersFromFile(path)).thenReturn(userMap);

        this.mvc.perform(get("/user/{resource}", "resource D")
                        .header("RoleInfo", ROLE_INFO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"statusCode\": 500," +
                        "\"message\": \"no such user\"," +
                        "\"data\": null" +
                        "}"));
    }

}