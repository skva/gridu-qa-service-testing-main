package com.griddynamics.gridu.qa.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class Parser {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static <T> T parseJson(String pathname, Class<T> objectClass) throws Exception {

        return MAPPER.readValue(new File(pathname), objectClass);
    }
}


