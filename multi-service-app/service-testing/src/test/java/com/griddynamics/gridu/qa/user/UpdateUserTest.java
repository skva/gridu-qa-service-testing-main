package com.griddynamics.gridu.qa.user;

import com.griddynamics.gridu.qa.util.Util;
import com.griddynamics.gridu.qa.util.Parser;
import com.griddynamics.gridu.qa.util.Service;
import com.griddynamics.payment.qa.gridu.springsoap.gen.*;
import org.testng.annotations.Test;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.soap.SOAPFaultException;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateUserTest extends Util {

    @Test
    public void updateUserTest() throws Exception {
        //Parser
        UserDetails userDetails =
                Parser.parseJson("src/test/resources/updateUser.json", UserDetails.class);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUserDetails(userDetails);

        Service service = new Service();
        UpdateUserResponse updateUserResponse = service.clientService().updateUser(updateUserRequest);

        //FieldByFieldWithIgnoringId
        assertThat(updateUserResponse.getUserDetails())
                .usingRecursiveComparison().ignoringExpectedNullFields()
                .isEqualTo(updateUserRequest);
    }

    @Test(expectedExceptions = {SOAPFaultException.class},
            expectedExceptionsMessageRegExp = "User with given id does not exist!")
    public void updateNonExistingUserTest() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setId(10);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUserDetails(userDetails);

        Service service = new Service();
        service.clientService().updateUser(updateUserRequest);
    }
}
