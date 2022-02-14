package com.griddynamics.gridu.qa.payment;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class UpdatePaymentTest {

    @Test
    public void updatePaymentTest() throws Exception {
        JSONObject body = new JSONObject();
        body.put("id", 3);
        body.put("cardNumber", "1333333333333333333");
        body.put("expiryYear", 2030);
        body.put("expiryMonth", 12);

        Response response = RestAssured.given().
                contentType(ContentType.JSON).
                body(body.toString()).
                post("http://localhost:8282/payment");

        response.prettyPrint();
    }
}
