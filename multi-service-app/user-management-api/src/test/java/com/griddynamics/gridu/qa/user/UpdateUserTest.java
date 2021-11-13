package com.griddynamics.gridu.qa.user;

import io.restassured.RestAssured;
import io.restassured.internal.util.IOUtils;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.FileInputStream;

import static io.restassured.RestAssured.given;

public class UpdateUserTest {

    @Test
    public void updateUserTest() throws Exception{
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/UpdateUserRequest.xml");
        RestAssured.baseURI="http://localhost:8080/ws";
        Response response = given().
                header("Content-Type", "text/xml").
                and().
                body(IOUtils.toByteArray(fileInputStream)).
                when().
                post();

        response.prettyPrint();
    }
}
