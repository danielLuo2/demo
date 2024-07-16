package com.ebay.demo.bean;

import com.ebay.demo.common.Role;
import lombok.Data;

/**
 * @Author xudong luo
 * @Date 2024/7/15
 * @Description //TODO
 */
@Data
public class UserInfo {
    private int userId;
    private String accountName;
    private Role role;
}
