package com.example.demo.controller;

import com.example.demo.db.request.LoginRequest;
import com.example.demo.db.request.RegisterUserRequest;
import com.example.demo.db.response.LoginResponse;
import com.example.demo.db.response.RegisterUserResponse;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registerUser")
    public RegisterUserResponse registerUser(@RequestBody RegisterUserRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.loginUser(request);
    }

}
