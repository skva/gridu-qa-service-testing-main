package com.griddynamics.gridu.qa.user;

import com.griddynamics.gridu.qa.util.Service;
import com.griddynamics.payment.qa.gridu.springsoap.gen.DeleteUserRequest;
import com.griddynamics.payment.qa.gridu.springsoap.gen.DeleteUserResponse;
import org.testng.annotations.Test;

public class DeleteUserTest {

    @Test
    public void deleteUserTest() throws Exception {
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setUserId(1);

        Service service = new Service();
        DeleteUserResponse deleteUserResponse = service.clientService().deleteUser(deleteUserRequest);
    }
}
