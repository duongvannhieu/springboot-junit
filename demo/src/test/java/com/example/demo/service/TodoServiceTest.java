package com.example.demo.service;

import com.example.demo.repository.TodoRepository;
import com.example.demo.db.Todo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoServiceTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoService todoService;

    @Test
    public void testSaveSuccess() {
        Todo todo = new Todo();
        todo.setTitle("Todo-2");
        todo.setDetail("Do homework");
        todoService.save(todo);
        Assert.assertNotNull(todo);
        Assert.assertTrue(todoRepository.findByTitle(todo.getTitle()) != null);
    }

    @Test
    public void testSaveFailWithTitleIsNull() {
        Todo todo = new Todo();
        todo.setTitle("");
        todo.setDetail("Do homework 1");
        todoService.save(todo);
        Assert.assertNotNull("INSERT FAIL", todo);
        Assert.assertFalse(todoRepository.findByTitle(todo.getTitle()) != null);
    }

    @Test
    public void testSaveFailWithDetailIsNull() {
        Todo todo = new Todo();
        todo.setTitle("to-do");
        todo.setDetail("");
        todoService.save(todo);
        Assert.assertNotNull("INSERT FAIL", todo);
        Assert.assertFalse(todoRepository.findByTitle(todo.getTitle()) != null);
    }

    @Test
    public void testSaveFailWithTitleExist() {
        Todo todo = new Todo();
        todo.setTitle("Todo-2");
        todo.setDetail("Do homework 1");
        todoService.save(todo);
        Assert.assertNotNull("INSERT FAIL", todo);
        Assert.assertTrue(todoRepository.findByTitle(todo.getTitle()) != null);
    }

}
