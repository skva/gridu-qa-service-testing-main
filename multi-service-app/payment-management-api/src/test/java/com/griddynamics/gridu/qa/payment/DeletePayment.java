package com.griddynamics.gridu.qa.payment;

import com.griddynamics.gridu.qa.payment.api.model.Payment;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class DeletePayment {

    @Test
    public void deletePaymentTest() throws Exception {
        //Create payment
        Payment payment = new Payment();
        payment.setCardNumber("11111111111111111111");
        payment.setExpiryYear(2033);
        payment.setExpiryMonth(11);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payment)
                .post("http://localhost:8282/payment");

        //Get paymentId of new payment
        int paymentId = response.jsonPath().getInt("id");

        //Delete payment
        Response responseDelete = RestAssured.delete("http://localhost:8282/payment/1");
        response.prettyPrint();
    }
}
