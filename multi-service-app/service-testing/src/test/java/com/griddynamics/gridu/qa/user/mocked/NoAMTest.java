package com.griddynamics.gridu.qa.user.mocked;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.griddynamics.gridu.qa.payment.api.PaymentApi;
import com.griddynamics.gridu.qa.payment.api.PaymentApiController;
import com.griddynamics.gridu.qa.payment.api.model.Payment;
import com.griddynamics.gridu.qa.payment.service.PaymentApiDelegateImpl;
import com.griddynamics.gridu.qa.util.DataBaseUtil;
import com.griddynamics.payment.qa.gridu.springsoap.gen.NewAddress;
import com.griddynamics.payment.qa.gridu.springsoap.gen.State;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class NoAMTest extends DataBaseUtil {

    private WireMockServer wireMockServer;

    @BeforeTest
    public void setUp() {
        wireMockServer = new WireMockServer(options().port(8008));
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
    }

    @Test
    public void createAddressMockedAM_Test() {
        WireMock.stubFor(WireMock.post(WireMock.urlPathEqualTo("/address"))
                .willReturn(WireMock.aResponse()
                        .withStatus(201)
                        .withHeader("Content-type", "application/json")
                        .withBodyFile("address.json")));

        NewAddress newAddress = new NewAddress();
        newAddress.setState(State.OR);
        newAddress.setCity("Portlanddd");
        newAddress.setLine1("Line111");
        newAddress.setZip("111112");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(newAddress)
                .post("http://localhost:8181/address");
    }


    @Test
    public void CreatePaymentMockedPM_Test() {
        WireMock.stubFor(WireMock.post(WireMock.urlPathEqualTo("/payment"))
                .willReturn(WireMock.aResponse()
                        .withStatus(201)
                        .withHeader("Content-type", "application/json")
                        .withBodyFile("payment.json")));

        Payment payment = new Payment();



    }

    @AfterTest
    public void tearDown() {
        wireMockServer.stop();
    }
}


