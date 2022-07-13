package com.griddynamics.gridu.qa.payment.mocked;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.griddynamics.gridu.qa.payment.api.model.Payment;
import com.griddynamics.gridu.qa.util.Util;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdatePaymentGatewayMockedTest extends Util {

    @Test
    public void updatePaymentMockTest() {
        //Configure mock for payment gateway
        WireMock.stubFor(WireMock.put(WireMock.anyUrl()).
                willReturn(ResponseDefinitionBuilder.responseDefinition().
                        withBody("Token111").withStatus(200).withHeader("Content-Type", "text/plain")));
        //Make data for call api
        Payment payment = new Payment();
        payment.setId(1l);
        payment.setCardNumber("61111111111111111113");
        payment.setExpiryYear(2033);
        payment.setExpiryMonth(12);
        //Call payment api
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payment)
                .put(Util.getWiremockPaymentURL() + "payment");
        //Assert
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(payment).usingRecursiveComparison()
                .ignoringExpectedNullFields().isEqualTo(response);

        JsonPath jsonPathEvaluator = response.jsonPath();
        Boolean ver = jsonPathEvaluator.get("verified");
        softly.assertThat(ver).isEqualTo(true);

        softly.assertAll();
    }

    @Test
    public void updatePaymentMockGatewayFailedTest() {
        //Configure mock for payment gateway
        WireMock.stubFor(WireMock.put(WireMock.anyUrl()).
                willReturn(ResponseDefinitionBuilder.responseDefinition().
                        withStatus(500)));
        //Make data for call api
        Payment payment = new Payment();
        payment.setId(5l);
        payment.setUserId(1l);
        payment.setCardNumber("61111111111111111113");
        payment.setExpiryYear(2033);
        payment.setExpiryMonth(12);
        //Call payment api
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payment)
                .put(Util.getWiremockPaymentURL() + "payment");
        //Assert
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(payment).usingRecursiveComparison()
                .ignoringExpectedNullFields().isEqualTo(response);

        JsonPath jsonPathEvaluator = response.jsonPath();
        Boolean ver = jsonPathEvaluator.get("verified");
        softly.assertThat(ver).isEqualTo(false);

        softly.assertAll();
    }

    @Test
    public void updatePaymentByInvalidIdTest() {
        long paymentId = Integer.MAX_VALUE;
        //Configure mock for payment gateway
        WireMock.stubFor(WireMock.post(WireMock.anyUrl()).
                willReturn(ResponseDefinitionBuilder.responseDefinition().
                        withBody("Token111").withStatus(200).withHeader("Content-Type", "text/plain")));
        //Make data for call api
        Payment payment = new Payment();
        payment.setId(paymentId);
        payment.setCardNumber("61111111111111111113");
        payment.setExpiryYear(2033);
        payment.setExpiryMonth(12);
        //Call payment api
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(payment)
                .put(Util.getWiremockPaymentURL() + "payment");
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    public void updatePaymentWithoutBodyTest() {
        //Configure mock for payment gateway
        WireMock.stubFor(WireMock.post(WireMock.anyUrl()).
                willReturn(ResponseDefinitionBuilder.responseDefinition().
                        withBody("Token111").withStatus(200).withHeader("Content-Type", "text/plain")));
        //Call payment api
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .put(Util.getWiremockPaymentURL() + "payment");
        assertThat(response.getStatusCode()).isEqualTo(405);
    }
}
