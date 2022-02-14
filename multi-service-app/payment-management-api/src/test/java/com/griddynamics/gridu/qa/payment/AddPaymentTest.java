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

        //old right
        //WireMock.stubFor(WireMock.get("/payment").willReturn(mockResponse));

        //Proxying
        WireMock.stubFor(WireMock.any(WireMock.anyUrl())
                .willReturn(WireMock.aResponse().proxiedFrom("http://localhost:8282")));




        //WireMock.givenThat(WireMock.get("/payment").willReturn(mockResponse));
    }

    @Test
    public void addPaymentTest() {
        Payment payment = new Payment();
        payment.setCardNumber("11111111111111111111");
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

    @Test
    public  void testMockTest() {
        Payment payment = new Payment();
        payment.setCardNumber("11111111111111111111");
        payment.setExpiryYear(2033);
        payment.setExpiryMonth(12);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payment)
                .post("http://localhost:8282/payment");

        Assert.assertEquals(response.statusCode(), 500);
    }

    @Test
    public void addWrongPaymentTest() {
        Payment payment = new Payment();

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payment)
                .post("http://localhost:8282/payment");
    }



//    @Test
//    public void addPaymentTestFirst() {
//        JSONObject body = new JSONObject();
//        body.put("cardNumber", "3333333333333333333");
//        body.put("expiryYear", 2030);
//        body.put("expiryMonth", 12);
//
//        Response response = RestAssured.given().
//                contentType(ContentType.JSON).
//                body(body.toString()).
//                post("http://localhost:8282/payment");
//
//        response.prettyPrint();
//    }
}
