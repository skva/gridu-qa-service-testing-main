package com.griddynamics.gridu.qa.payment;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.griddynamics.gridu.qa.payment.api.model.Payment;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddPaymentTest {

    private static final String HOST = "localhost";
    private static final int PORT = 8989;
    private static WireMockServer server = new WireMockServer(PORT);

//    @BeforeClass
//    public void initServer() {
//        System.out.println("Init");
//
//        server.start();
//        WireMock.configureFor(HOST, PORT);
//
//        ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder();
//        mockResponse.withStatus(200);
//
//        //Proxying
//        WireMock.stubFor(WireMock.any(WireMock.anyUrl())
//                .willReturn(WireMock.aResponse().proxiedFrom("http://localhost:8989")));
//    }

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

        Assert.assertEquals(payment.getCardNumber(),
                response.jsonPath().getString("cardNumber"));
    }

    @Test
    public void addPaymentMockTest() {
        //Configure mock for payment gateway
        WireMock.stubFor(WireMock.post(WireMock.anyUrl()).
                willReturn(ResponseDefinitionBuilder.responseDefinition().
                        withBody("Token111").withStatus(200).withHeader("Content-Type", "text/plain")));
        //Make data for call api
        Payment payment = new Payment();
        payment.setCardNumber("11111111111111111113");
        payment.setExpiryYear(2033);
        payment.setExpiryMonth(12);
        //Call payment api
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payment)
                .post("http://localhost:8282/payment");
        //Assert
        //Assert fields
    }
}
