package com.griddynamics.gridu.qa.user;

import com.griddynamics.payment.qa.gridu.springsoap.gen.DeleteUserRequest;
import com.griddynamics.payment.qa.gridu.springsoap.gen.DeleteUserResponse;
import org.testng.annotations.Test;

public class DeleteUserTest extends BaseTest{

    @Test
    public void deleteUserTest() throws Exception {
        DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.setUserId(32);

        DeleteUserResponse deleteUserResponse = clientService().deleteUser(deleteUserRequest);
    }
}
