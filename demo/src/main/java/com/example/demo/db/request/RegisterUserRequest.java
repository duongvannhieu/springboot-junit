package com.example.demo.db.request;

import lombok.Data;

@Data
public class RegisterUserRequest {

    private String username;

    private String email;

    private String password;

}
