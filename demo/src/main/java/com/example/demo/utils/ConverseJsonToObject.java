package com.example.demo.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverseJsonToObject {

    public <T> T mapFromJson(String json, Class<T> tClass)
            throws JsonMappingException, JsonParseException, Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, tClass);
    }

}
