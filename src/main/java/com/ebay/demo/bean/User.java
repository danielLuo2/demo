package com.ebay.demo.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author xudong luo
 * @Date 2024/7/15
 * @Description //TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @NotNull(message = "User ID cannot be null")
    private int userId;

    @NotEmpty(message = "At least one resource must be added")
    @JsonProperty("endpoint")
    private List<String> endPoint;
}
