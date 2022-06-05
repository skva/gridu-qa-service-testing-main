package com.griddynamics.gridu.qa.user;

import com.griddynamics.gridu.qa.util.DataBaseUtil;
import com.griddynamics.gridu.qa.util.Service;
import com.griddynamics.payment.qa.gridu.springsoap.gen.DeleteUserRequest;
import org.testng.annotations.Test;

import javax.xml.ws.soap.SOAPFaultException;

public class DeleteUserTest extends DataBaseUtil {

    @Test
    public void deleteUserTest() throws Exception {
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setUserId(5);

        Service service = new Service();
        service.clientService().deleteUser(deleteUserRequest);
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
