package com.griddynamics.gridu.qa.payment.mocked;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.griddynamics.gridu.qa.payment.api.model.Payment;
import com.griddynamics.gridu.qa.util.Util;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddPaymentGatewayMockedTest extends Util {

    @Test
    public void addPaymentMockTest() {
        //Configure mock for payment gateway
        WireMock.stubFor(WireMock.post(WireMock.anyUrl()).
                willReturn(ResponseDefinitionBuilder.responseDefinition().
                        withBody("Token111").withStatus(200).withHeader("Content-Type", "text/plain")));
        //Make data for call api
        Payment payment = new Payment();
        payment.setUserId(1L);
        payment.setCardNumber("51111111111111111113");
        payment.setExpiryYear(2033);
        payment.setExpiryMonth(12);
        payment.setCvv("555");
        payment.setCardHolder("Name");
        payment.setVerified(true);
        //Call payment api
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payment)
                .post(Util.getWiremockPaymentURL() + "payment");
        //Assert
        assertThat(payment).usingRecursiveComparison()
                .ignoringExpectedNullFields().isEqualTo(response);
        assertThat(response.getStatusCode()).isEqualTo(201);
    }

    @Test
    public void addPaymentWithoutBodyMockTest() {
        //Configure mock for payment gateway
        WireMock.stubFor(WireMock.post(WireMock.anyUrl()).
                willReturn(ResponseDefinitionBuilder.responseDefinition().
                        withBody("Token111").withStatus(200).withHeader("Content-Type", "text/plain")));
        //Call payment api
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .post(Util.getWiremockPaymentURL() + "payment");
        assertThat(response.getStatusCode()).isEqualTo(405);
    }
}
