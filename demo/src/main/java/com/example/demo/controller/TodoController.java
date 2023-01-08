package com.example.demo.controller;

import com.example.demo.db.Todo;
import com.example.demo.service.JwtToken;
import com.example.demo.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TodoController {
    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private TodoService todoService;

    @GetMapping("/getAllTodo")
    public List<Todo> getAll(@RequestHeader(name = AUTHORIZATION) String jwt) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        if (JwtToken.parseJwt(jwt.substring(7)) && StringUtils.hasText(jwt)) {
            return todoService.findAll();
        }
        return null;
    }

    @PostMapping("/saveTodo")
    public String saveTodo(@RequestBody Todo todo) {
        return todoService.save(todo);
    }

    @PutMapping("/editTodo/{id}")
    public String editTodo(@PathVariable Long id, @RequestBody Todo todo) {
        return todoService.edit(todo, id);
    }

}
