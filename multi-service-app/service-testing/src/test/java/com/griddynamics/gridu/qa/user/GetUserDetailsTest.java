package com.griddynamics.gridu.qa.user;

import com.griddynamics.gridu.qa.util.DataBaseUtil;
import com.griddynamics.gridu.qa.util.Service;
import com.griddynamics.payment.qa.gridu.springsoap.gen.GetUserDetailsRequest;
import com.griddynamics.payment.qa.gridu.springsoap.gen.GetUserDetailsResponse;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GetUserDetailsTest extends DataBaseUtil {

    @Test
    public void getUserDetailsTest() throws Exception {
        GetUserDetailsRequest getUserDetailsRequest = new GetUserDetailsRequest();
        getUserDetailsRequest.setUserId(100);

        Service service = new Service();
        GetUserDetailsResponse getUserDetailsResponse =
                service.clientService().getUserDetails(getUserDetailsRequest);

        //WHY PASS IF ID 100???
        assertThat(getUserDetailsResponse).hasNoNullFieldsOrProperties();
    }

    @Test
    public void getNonExistingUserDetailsTest() throws Exception {
        GetUserDetailsRequest getUserDetailsRequest = new GetUserDetailsRequest();
        getUserDetailsRequest.setUserId(100);

        Service service = new Service();
        GetUserDetailsResponse getUserDetailsResponse =
                service.clientService().getUserDetails(getUserDetailsRequest);

        assertThat(getUserDetailsResponse.getUserDetails().getName()).isEqualTo("NOT_FOUND");
        assertThat(getUserDetailsResponse.getUserDetails().getLastName()).isEqualTo("NOT_FOUND");
        assertThat(getUserDetailsResponse.getUserDetails().getEmail()).isEqualTo("NOT_FOUND");
    }
}
