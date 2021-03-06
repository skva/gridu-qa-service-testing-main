package com.griddynamics.gridu.qa.payment;

import com.griddynamics.gridu.qa.payment.api.model.Payment;
import com.griddynamics.gridu.qa.util.Util;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddPaymentTest extends Util {

    @Test
    public void addPaymentTest() {
        Payment payment = new Payment();
        payment.setCardNumber("11111111111111111114");
        payment.setExpiryYear(2033);
        payment.setExpiryMonth(12);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payment)
                .post("http://localhost:8282/payment");

        assertThat(payment).usingRecursiveComparison()
                .ignoringExpectedNullFields().isEqualTo(response);
    }
}