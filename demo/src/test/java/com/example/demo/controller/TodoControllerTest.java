package com.example.demo.controller;

import com.example.demo.repository.db.Todo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class TodoControllerTest extends AbstractTest{

    @Autowired
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testSaveRequest() throws Exception{
        String jsonInput = super.mapToJson(new Todo("todo-12", "HIHI"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/saveTodo")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonInput))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(content, "SAVE TO SUCCESS");
    }

    @Test
    public void testGetRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/getAllTodo")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Todo[] todos = super.mapFromJson(content, Todo[].class);
        Assert.assertTrue(todos.length > 0);
    }

    @Test
    public void testPutRequest() throws Exception {
        String jsonInput = super.mapToJson(new Todo("todo-12", "CODE DI"));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/editTodo/19")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonInput))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(content, "EDIT TODO SUCCESS");
    }

}
