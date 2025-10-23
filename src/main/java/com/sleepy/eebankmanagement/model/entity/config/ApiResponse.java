package com.sleepy.eebankmanagement.model.entity.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ApiResponse {
    private int status;
    private String message;
    private Object data;
}
