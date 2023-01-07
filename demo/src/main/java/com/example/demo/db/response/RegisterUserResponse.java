package com.example.demo.db.response;

import lombok.Data;

@Data
public class RegisterUserResponse {
    private String username;

    private String email;

    private String msg;
}
