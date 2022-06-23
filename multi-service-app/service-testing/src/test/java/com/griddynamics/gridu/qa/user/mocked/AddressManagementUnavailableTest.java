package com.griddynamics.gridu.qa.user.mocked;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.griddynamics.gridu.qa.util.Util;
import com.griddynamics.gridu.qa.util.Parser;
import com.griddynamics.gridu.qa.util.Service;
import com.griddynamics.payment.qa.gridu.springsoap.gen.*;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import javax.xml.ws.soap.SOAPFaultException;

public class AddressManagementUnavailableTest extends Util {

    @Test(expectedExceptions = {SOAPFaultException.class},
            expectedExceptionsMessageRegExp = "Can not save user addresses!")
    public void createUserWithNotResponseAM_Test() throws Exception {
        WireMock.stubFor(WireMock.any(WireMock.urlPathMatching("/address/.*"))
                .willReturn(WireMock.aResponse()
                        .withStatus(500)));

        CreateUserRequest createUserRequest = Parser.parseJson("src/test/resources/createUser.json", CreateUserRequest.class);

        Service service = new Service();
        service.clientService().createUser(createUserRequest);
    }

    @Test(expectedExceptions = {SOAPFaultException.class},
            expectedExceptionsMessageRegExp = "Can not save user addresses!")
    public void updateUserWithNotResponseAM_Test() throws Exception{
        WireMock.stubFor(WireMock.any(WireMock.urlPathMatching("/address/.*"))
                .willReturn(WireMock.aResponse()
                        .withStatus(500)));

        UserDetails userDetails = Parser.parseJson("src/test/resources/updateUser.json", UserDetails.class);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUserDetails(userDetails);

        Service service = new Service();
        service.clientService().updateUser(updateUserRequest);
    }

    @Test
    public void getUserDetailsWithNotResponseAM_Test() throws Exception {
        WireMock.stubFor(WireMock.any(WireMock.urlPathMatching("/address/.*"))
                .willReturn(WireMock.aResponse()
                        .withStatus(500)));

        GetUserDetailsRequest getUserDetailsRequest = new GetUserDetailsRequest();
        getUserDetailsRequest.setUserId(1);

        Service service = new Service();
        GetUserDetailsResponse getUserDetailsResponse =
                service.clientService().getUserDetails(getUserDetailsRequest);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(getUserDetailsResponse).hasNoNullFieldsOrProperties();
        softly.assertThat(getUserDetailsResponse.getUserDetails().getAddresses().getAddress()).isEmpty();
        softly.assertThat(getUserDetailsResponse.getUserDetails().getPayments().getPayment()).isNotEmpty();
        softly.assertAll();
    }

    @Test(expectedExceptions = {SOAPFaultException.class},
            expectedExceptionsMessageRegExp = "Can not delete user's payments and/or addresses")
    public void deleteUserWithNotResponseAM_Test() throws Exception{
        WireMock.stubFor(WireMock.any(WireMock.urlPathMatching("/address/.*"))
                .willReturn(WireMock.aResponse()
                        .withStatus(500)));

        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setUserId(1);

        Service service = new Service();
        service.clientService().deleteUser(deleteUserRequest);
    }
}
