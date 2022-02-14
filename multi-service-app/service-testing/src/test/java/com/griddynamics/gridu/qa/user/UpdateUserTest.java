package com.griddynamics.gridu.qa.user;

import com.griddynamics.payment.qa.gridu.springsoap.gen.*;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class UpdateUserTest extends BaseTest{

    @Test
    public void updateUserTest() throws Exception {
        //XMLGregorianCalendar date
        XMLGregorianCalendar birthday = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar("2012-12-12+04:00");

        //Address
        UserDetails.Addresses address = new UserDetails.Addresses();
        address.getAddress().add(updateExistingAddress());

        //Payment
        UserDetails.Payments payment = new UserDetails.Payments();
        payment.getPayment().add(updateExistingPayment());

        //Update user with Address and Payment
        UserDetails userDetails = new UserDetails();
        userDetails.setId(42l);
        userDetails.setName("Jake");
        userDetails.setLastName("Black");
        userDetails.setEmail("b@b.b");
        userDetails.setBirthday(birthday);
        userDetails.setAddresses(address);
        userDetails.setPayments(payment);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUserDetails(userDetails);

        UpdateUserResponse updateUserResponse = clientService().updateUser(updateUserRequest);

        //Assertions

        //UserData
        Assertions.assertEquals(updateUserResponse.getUserDetails().getName(), userDetails.getName());
        Assertions.assertEquals(updateUserResponse.getUserDetails().getLastName(), userDetails.getLastName());
        Assertions.assertEquals(updateUserResponse.getUserDetails().getEmail(), userDetails.getEmail());
        Assertions.assertEquals(updateUserResponse.getUserDetails().getBirthday(), userDetails.getBirthday());

//        //AddressData
//        Assertions.assertEquals(updateUserResponse.getUserDetails().getAddresses(), userDetails.getAddresses());
//        //PaymentData
//        Assertions.assertEquals(updateUserResponse.getUserDetails().getPayments(), userDetails.getPayments());
    }
}
