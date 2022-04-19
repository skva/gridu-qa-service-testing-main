package com.griddynamics.gridu.qa.user;

import com.griddynamics.payment.qa.gridu.springsoap.gen.*;

//No need anymore? :(
public class BaseTest {


    public ExistingPayment updateExistingPayment() {
        ExistingPayment existingPayment = new ExistingPayment();
        existingPayment.setId(42l);
        existingPayment.setCardNumber("555");
        existingPayment.setExpiryYear(1111);
        existingPayment.setExpiryMonth(11);
        existingPayment.setCardholder("dsadsa");
        existingPayment.setCvv("333");
        return existingPayment;
    }
}
