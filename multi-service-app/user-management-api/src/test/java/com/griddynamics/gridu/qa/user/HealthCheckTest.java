package com.griddynamics.gridu.qa.user;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class HealthCheckTest {

    @Test
    public void healthCheckTest() {
        given().when().get("http://localhost:8080/ws/users.wsdl").
                then().
                assertThat().
                statusCode(200);
    }

}
