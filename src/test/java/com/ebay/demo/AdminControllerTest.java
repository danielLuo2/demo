package com.ebay.demo;

import com.ebay.demo.bean.User;
import com.ebay.demo.controller.AdminController;
import com.ebay.demo.service.UserService;

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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author xudong luo
 * @Date 2024/7/16
 * @Description //TODO
 */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    private static final String ROLE_INFO = "eyJ1c2VySWQiOjEyMzQ1NiwiYWNjb3VudE5hbWUiOiJEYW5pZWwiLCJyb2xlIjoiQURNSU4ifQ==";
    private static final String ADD_USER_BODY = "{\n" +
            "    \"userId\": 1,\n" +
            "    \"endpoint\": [\n" +
            "        \"resource A\",\n" +
            "        \"resource B\",\n" +
            "        \"resource C\"\n" +
            "    ]\n" +
            "}";
    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private AdminController adminController;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }


    @Test
    public void testAddUser_WhenUserDoesNotExist_ShouldAddUserAndReturnSuccess() throws Exception {
        String userFilePath = "testUsers.txt";
        ReflectionTestUtils.setField(adminController, "userFilePath", userFilePath);

        this.mvc.perform(post("/admin/addUser")
                        .header("RoleInfo", ROLE_INFO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ADD_USER_BODY))
                .andExpect(status().isOk())
                //对接口响应进行验证
                .andExpect(content().json("{" +
                        "\"statusCode\": 200," +
                        "\"message\": \"Success\"," +
                        "\"data\": \"OK\"" +
                        "}"));
    }

    @Test
    public void testAddUser_WhenUserAlreadyExists_ShouldReturnAlreadyExistError() throws Exception {
        String userFilePath = "testUsers.txt";
        Path path = Paths.get(userFilePath);

        Map<Integer, User> userMap = new HashMap<Integer, User>();
        User user = new User(1, List.of("resource A", "resource B", "resource C"));
        userMap.put(1, user);
//        Mockito.doNothing().when(userService).checkFileExists(path);
        when(userService.readUsersFromFile(path)).thenReturn(userMap);
//        Mockito.doNothing().when(userService).addToFile(path, user);

        ReflectionTestUtils.setField(adminController, "userFilePath", userFilePath);

        this.mvc.perform(post("/admin/addUser")
                        .header("RoleInfo", ROLE_INFO)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ADD_USER_BODY))
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"statusCode\": 200," +
                        "\"message\": \"already exist\"," +
                        "\"data\": null" +
                        "}"));
    }


}