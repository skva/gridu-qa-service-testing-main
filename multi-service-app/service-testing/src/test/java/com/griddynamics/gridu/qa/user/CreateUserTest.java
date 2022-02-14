package com.griddynamics.gridu.qa.user;

import com.griddynamics.payment.qa.gridu.springsoap.gen.*;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class CreateUserTest extends BaseTest{

    @Test
    public void createUserTest() throws Exception{
        //XMLGregorianCalendar birthday
        XMLGregorianCalendar birthday = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar("2014-01-07+04:00");

        //Address
        CreateUserRequest.Addresses address = new CreateUserRequest.Addresses();
        address.getAddress().add(createNewAddress());

        //Payment
        CreateUserRequest.Payments payment = new CreateUserRequest.Payments();
        payment.getPayment().add(createNewPayment());

        //New user with Address and Payment
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setName("Jack");
        createUserRequest.setLastName("Yellow");
        createUserRequest.setEmail("a@a.a");
        createUserRequest.setBirthday(birthday);
        createUserRequest.setAddresses(address);
        createUserRequest.setPayments(payment);

        CreateUserResponse createUserResponse = clientService().createUser(createUserRequest);

        //Assertions

        //Id is not null ???
        Assertions.assertNotNull(createUserResponse.getUserDetails().getId());
        //UserData
        Assertions.assertEquals(createUserResponse.getUserDetails().getName(), createUserRequest.getName());
        Assertions.assertEquals(createUserResponse.getUserDetails().getLastName(), createUserRequest.getLastName());
        Assertions.assertEquals(createUserResponse.getUserDetails().getEmail(), createUserRequest.getEmail());
        Assertions.assertEquals(createUserResponse.getUserDetails().getBirthday(), createUserRequest.getBirthday());

//        //AddressData
//        Assertions.assertEquals(createUserResponse.getUserDetails().getAddresses(), createUserRequest.getAddresses());
//        //PaymentData
//        Assertions.assertEquals(createUserResponse.getUserDetails().getPayments(), createUserRequest.getPayments());
    }

    @Test
    public void createUserTestSeparate() throws Exception{

    }
}
