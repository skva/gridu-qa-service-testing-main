package com.griddynamics.gridu.qa.user;

import com.griddynamics.gridu.qa.util.Util;
import com.griddynamics.gridu.qa.util.Service;
import com.griddynamics.payment.qa.gridu.springsoap.gen.DeleteUserRequest;
import com.griddynamics.payment.qa.gridu.springsoap.gen.GetUserDetailsRequest;
import com.griddynamics.payment.qa.gridu.springsoap.gen.GetUserDetailsResponse;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import javax.xml.ws.soap.SOAPFaultException;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteUserTest extends Util {

    @Test
    public void deleteUserTest() throws Exception {
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setUserId(5);

        Service service = new Service();
        service.clientService().deleteUser(deleteUserRequest);

        // Assert
        SoftAssertions softly = new SoftAssertions();

        GetUserDetailsRequest getUserDetailsRequest = new GetUserDetailsRequest();
        getUserDetailsRequest.setUserId(5);
        GetUserDetailsResponse getUserDetailsResponse =
                service.clientService().getUserDetails(getUserDetailsRequest);

        assertThat(getUserDetailsResponse.getUserDetails().getName()).isEqualTo("NOT_FOUND");
        assertThat(getUserDetailsResponse.getUserDetails().getLastName()).isEqualTo("NOT_FOUND");
        assertThat(getUserDetailsResponse.getUserDetails().getEmail()).isEqualTo("NOT_FOUND");

        softly.assertAll();
    }

    @Test(expectedExceptions = {SOAPFaultException.class},
            expectedExceptionsMessageRegExp = "User with given id does not exist!")
    public void deleteNonExistingUserTest() throws Exception {
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setUserId(10);

        Service service = new Service();
        service.clientService().deleteUser(deleteUserRequest);
    }

    @Test(expectedExceptions = {SOAPFaultException.class},
            expectedExceptionsMessageRegExp = "Can not delete user's payments and/or addresses")
    public void deleteUserWithAddressPaymentTest() throws Exception {
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setUserId(1);

        Service service = new Service();
        service.clientService().deleteUser(deleteUserRequest);
    }
}
