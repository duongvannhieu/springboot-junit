package com.example.demo.controller;

import com.example.demo.db.request.LoginRequest;
import com.example.demo.db.request.RegisterUserRequest;
import com.example.demo.db.response.LoginResponse;
import com.example.demo.db.response.RegisterUserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

public class UserControllerTest extends AbstractTest{

    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
    private final String URL_REGISTER_USER = "/api/v1/registerUser";
    private final String URL_LOGIN = "/api/v1/login";

    @Autowired
    @Before
    public void setUp() {
        super.setUp();
    }

    private String jsonInput(RegisterUserRequest request) throws JsonProcessingException {
        return super.mapToJson(request);
    }

    private String jsonInputLogin(LoginRequest request) throws JsonProcessingException {
        return super.mapToJson(request);
    }

    @Test
    public void registerUserSuccess() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("nhieu1");
        request.setEmail("nhieuEmail1");
        request.setPassword("123");
        MvcResult mvcResult = super.mockMvc.perform(MockMvcRequestBuilders.post(URL_REGISTER_USER)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonInput(request)))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String jsonOutput = mvcResult.getResponse().getContentAsString();
        RegisterUserResponse registerUserResponse = super.mapFromJson(jsonOutput, RegisterUserResponse.class);
        String msg = registerUserResponse.getMsg();
        Assert.assertEquals("REGISTER_USER_SUCCESS", msg);
    }

    @Test
    public void usernameExistsTest() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("nhieu1");
        request.setEmail("nhieuEmail1");
        request.setPassword("123");
        MvcResult mvcResult = super.mockMvc.perform(MockMvcRequestBuilders.post(URL_REGISTER_USER)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonInput(request)))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String jsonOutput = mvcResult.getResponse().getContentAsString();
        RegisterUserResponse registerUserResponse = super.mapFromJson(jsonOutput, RegisterUserResponse.class);
        String msg = registerUserResponse.getMsg();
        Assert.assertEquals("USERNAME_EXISTS", msg);
    }

    @Test
    public void emailExistsTest() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("nhieu1");
        request.setEmail("nhieuEmail1");
        request.setPassword("123");
        MvcResult mvcResult = super.mockMvc.perform(MockMvcRequestBuilders.post(URL_REGISTER_USER)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonInput(request)))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String jsonOutput = mvcResult.getResponse().getContentAsString();
        RegisterUserResponse registerUserResponse = super.mapFromJson(jsonOutput, RegisterUserResponse.class);
        String msg = registerUserResponse.getMsg();
        Assert.assertEquals("EMAIL_EXISTS", msg);
    }

    @Test
    public void usernameIsEmptyTest() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("nhieu1");
        request.setEmail("nhieuEmail1");
        request.setPassword("123");
        MvcResult mvcResult = super.mockMvc.perform(MockMvcRequestBuilders.post(URL_REGISTER_USER)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonInput(request)))
                .andReturn();
        validateEmpty(mvcResult);
    }

    @Test
    public void emailIsEmptyTest() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("nhieu1");
        request.setEmail("nhieuEmail1");
        request.setPassword("123");
        MvcResult mvcResult = super.mockMvc.perform(MockMvcRequestBuilders.post(URL_REGISTER_USER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonInput(request)))
                .andReturn();
        validateEmpty(mvcResult);
    }

    @Test
    public void passwordIsEmptyTest() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("nhieu1");
        request.setEmail("nhieuEmail1");
        request.setPassword("123");
        MvcResult mvcResult = super.mockMvc.perform(MockMvcRequestBuilders.post(URL_REGISTER_USER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonInput(request)))
                .andReturn();
        validateEmpty(mvcResult);
    }

    private void validateEmpty(MvcResult mvcResult) throws IOException {
        String jsonInput = mvcResult.getResponse().getContentAsString();
        RegisterUserResponse registerUserResponse = super.mapFromJson(jsonInput, RegisterUserResponse.class);
        String msg = registerUserResponse.getMsg();
        Assert.assertEquals("USERNAME_EMAIL_PASSWORD_IS_NOT_EMPTY", msg);
    }

    @Test
    public void loginTest() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("nhieu12");
        request.setPassword("123");

        MvcResult mvcResult = super.mockMvc.perform(MockMvcRequestBuilders.post(URL_LOGIN)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonInputLogin(request)))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(200, status);
        String jsonOutput = mvcResult.getResponse().getContentAsString();
        LoginResponse registerUserResponse = super.mapFromJson(jsonOutput, LoginResponse.class);
        logger.info(registerUserResponse.getJwt());
        Assert.assertEquals("Login Success", registerUserResponse.getJwt());
    }

}
