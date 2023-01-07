package com.example.demo.controller;

import com.example.demo.db.Todo;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/getAllTodo")
    public List<Todo> getAll() {
        return todoService.findAll();
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
