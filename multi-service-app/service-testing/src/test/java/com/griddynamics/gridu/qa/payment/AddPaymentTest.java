package com.griddynamics.gridu.qa.payment;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.griddynamics.gridu.qa.payment.api.model.Payment;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AddPaymentTest {

    private static final String HOST = "localhost";
    private static final int PORT = 8088;
    private static WireMockServer server = new WireMockServer(PORT);

    @BeforeClass
    public void initServer() {
        System.out.println("Init");

        server.start();
        WireMock.configureFor(HOST, PORT);

        ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder();
        mockResponse.withStatus(200);

        //Proxying
        WireMock.stubFor(WireMock.any(WireMock.anyUrl())
                .willReturn(WireMock.aResponse().proxiedFrom("http://localhost:8282")));
    }

    @Test
    public void addPaymentTest() {
        Payment payment = new Payment();
        payment.setCardNumber("11111111111111111113");
        payment.setExpiryYear(2033);
        payment.setExpiryMonth(12);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payment)
                .post("http://localhost:8282/payment");

        Assert.assertEquals(payment.getCardNumber(),
                response.jsonPath().getString("cardNumber"));
    }

    @Test
    public void addPaymentTestMock() {
        String testApi = "http://localhost:" + PORT + "/payment";

        Response response = RestAssured.given()
                .get("http://localhost:8088/payment")
                .then()
                .statusCode(500)
                .extract().response();

        Assert.assertEquals(response.statusCode(), 500);
    }
}
