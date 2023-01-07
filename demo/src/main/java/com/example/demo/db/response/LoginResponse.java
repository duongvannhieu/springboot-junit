package com.example.demo.db.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String username;
    private String jwt;
}
