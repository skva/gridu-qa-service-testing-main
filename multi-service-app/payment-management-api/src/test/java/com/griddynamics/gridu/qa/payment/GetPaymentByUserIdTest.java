package com.griddynamics.gridu.qa.payment;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class GetPaymentByUserIdTest {

    @Test
    public void getPaymentByUserIdTest() throws Exception {
        Response response = RestAssured.get("http://localhost:8282/payment/15");
        response.prettyPrint();
    }
}
