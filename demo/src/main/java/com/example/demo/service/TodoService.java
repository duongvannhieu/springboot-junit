package com.example.demo.service;

import com.example.demo.repository.TodoRepository;
import com.example.demo.db.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public String save(Todo todoRequest) {
        Todo todo = new Todo();
        if (todoRequest.getTitle().isEmpty() || todoRequest.getDetail().isEmpty()) {
            return "TITLE OR DETAIL IS NOT EMPTY";
        } else if (todoRepository.findByTitle(todoRequest.getTitle()) != null) {
            return "TITLE ALREADY EXIST";
        } else {
            todo.setTitle(todoRequest.getTitle());
            todo.setDetail(todoRequest.getDetail());
            todoRepository.save(todo);
            return "SAVE TO SUCCESS";
        }
    }

    public String edit(Todo todoRequest, Long id) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isPresent()) {
            if (todoRequest.getTitle().isEmpty() || todoRequest.getDetail().isEmpty()) {
                return "TITLE OR DETAIL IS NOT EMPTY";
            } else {
                todo.get().setTitle(todoRequest.getTitle());
                todo.get().setDetail(todoRequest.getDetail());
                todoRepository.save(todo.get());
                return "EDIT TODO SUCCESS";
            }
        } else {
            return "EDIT TODO FAIL";
        }
    }

}
