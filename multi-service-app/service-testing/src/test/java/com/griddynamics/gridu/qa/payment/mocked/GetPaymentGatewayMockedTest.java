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

public class GetPaymentGatewayMockedTest extends Util {

    @Test
    public void getPaymentMockTest() {
        //Configure mock for payment gateway
        WireMock.stubFor(WireMock.post(WireMock.anyUrl()).
                willReturn(ResponseDefinitionBuilder.responseDefinition().
                        withBody("Token111").withStatus(200).withHeader("Content-Type", "text/plain")));
        //Make data for call api
        Payment payment = new Payment();
        payment.setId(1L);
        //Call payment api
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payment)
                .get("http://localhost:8282/payment");
        //Assert
        assertThat(payment).usingRecursiveComparison()
                .ignoringExpectedNullFields().isEqualTo(response);
    }
}
