package com.griddynamics.gridu.qa.payment.mocked;

import com.griddynamics.gridu.qa.payment.api.model.Payment;
import com.griddynamics.gridu.qa.util.Util;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetPaymentTest extends Util {

    @Test
    public void getPaymentByUserIdTest() {
        //Call payment api
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .get("http://localhost:8282/payment/1");

        List<Payment> paymentsListFromResponse = response.getBody().jsonPath()
                .getList(".", Payment.class);

        //Assert
        SoftAssertions assertSoftly = new SoftAssertions();

        assertThat(response).hasNoNullFieldsOrProperties();
        assertThat(paymentsListFromResponse).doesNotContainNull();
        paymentsListFromResponse
                .forEach(payment -> assertSoftly.assertThat(payment.getUserId()).isEqualTo(1));

        assertSoftly.assertAll();
    }

    @Test
    public void getPaymentByInvalidUserIdTest() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .get("http://localhost:8282/payment/invalidId");

        assertThat(response.getStatusCode()).isEqualTo(400);
    }

    @Test
    public void getPaymentByNonExistingUserIdTest() {
        long userId = Integer.MAX_VALUE;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .get("http://localhost:8282/payment/" + userId);

        assertThat(response.getStatusCode()).isEqualTo(405);
    }
}
