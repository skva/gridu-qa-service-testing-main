package com.griddynamics.gridu.qa.payment.mocked;

import com.griddynamics.gridu.qa.util.Util;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeletePaymentTest extends Util {

    @Test
    public void deletePaymentByUserIdTest() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .delete("http://localhost:8282/payment/1");
        //Assert
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .get("http://localhost:8282/payment/1");

        assertThat(response.getStatusCode()).isEqualTo(405);
    }

    @Test
    public void deletePaymentByInvalidUserIdTest() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .delete("http://localhost:8282/payment/invalidId");

        assertThat(response.getStatusCode()).isEqualTo(400);
    }

    @Test
    public void deletePaymentByNonExistingUserIdTest() {
        long userId = Integer.MAX_VALUE;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .delete("http://localhost:8282/payment/" + userId);

        assertThat(response.getStatusCode()).isEqualTo(404);
    }
}
