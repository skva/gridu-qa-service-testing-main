package com.griddynamics.gridu.qa.user;

import com.griddynamics.payment.qa.gridu.springsoap.gen.GetUserDetailsRequest;
import com.griddynamics.payment.qa.gridu.springsoap.gen.GetUserDetailsResponse;
import org.testng.annotations.Test;

public class GetUserDetailsTest extends BaseTest{

    @Test
    public void getUserDetailsTest() throws Exception {
        GetUserDetailsRequest getUserDetailsRequest = new GetUserDetailsRequest();
        getUserDetailsRequest.setUserId(33);

        GetUserDetailsResponse getUserDetailsResponse = clientService().getUserDetails(getUserDetailsRequest);

        //Assert GET not_found
    }
}
