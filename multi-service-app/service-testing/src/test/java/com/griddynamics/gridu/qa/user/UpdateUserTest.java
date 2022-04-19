package com.griddynamics.gridu.qa.user;

import com.griddynamics.gridu.qa.util.Parser;
import com.griddynamics.gridu.qa.util.Service;
import com.griddynamics.payment.qa.gridu.springsoap.gen.*;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import static com.griddynamics.payment.qa.gridu.springsoap.gen.State.OR;

public class UpdateUserTest {

    @Test
    public void updateUserTest() throws Exception {
        //Parser
        Parser parser = new Parser();
        Document xml = parser.parseFileU();

        //XMLGregorianCalendar date
        XMLGregorianCalendar birthday = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(xml.getElementsByTagName("birthday").item(0).getTextContent());

        //Address
        UserDetails.Addresses address = new UserDetails.Addresses();
        ExistingAddress existingAddress = new ExistingAddress();
        existingAddress.setState(OR); //Required State instead String
        existingAddress.setCity(xml.getElementsByTagName("city").item(0).getTextContent());
        existingAddress.setZip(xml.getElementsByTagName("zip").item(0).getTextContent());
        existingAddress.setLine1(xml.getElementsByTagName("line1").item(0).getTextContent());
        existingAddress.setLine2(xml.getElementsByTagName("line2").item(0).getTextContent());
        address.getAddress().add(existingAddress);

        //Payment
        UserDetails.Payments payment = new UserDetails.Payments();
        ExistingPayment existingPayment = new ExistingPayment();
        existingPayment.setCardNumber(xml.getElementsByTagName("cardNumber").item(0).getTextContent());
        existingPayment.setExpiryYear(2222); //Required int instead String
        existingPayment.setExpiryMonth(12); //Required int instead String
        existingPayment.setCardholder(xml.getElementsByTagName("cardholder").item(0).getTextContent());
        existingPayment.setCvv(xml.getElementsByTagName("cvv").item(0).getTextContent());
        payment.getPayment().add(existingPayment);

        //New user with Address and Payment
        UserDetails userDetails = new UserDetails();

//        for (int i = 0; i < userDetails.getId(1); i++) {
//            userDetails.getId(1);
//        }

        userDetails.setId(83l); //Required long instead String
        userDetails.setName(xml.getElementsByTagName("name").item(0).getTextContent());
        userDetails.setLastName(xml.getElementsByTagName("lastName").item(0).getTextContent());
        userDetails.setEmail(xml.getElementsByTagName("email").item(0).getTextContent());
        userDetails.setBirthday(birthday);
        userDetails.setAddresses(address);
        userDetails.setPayments(payment);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUserDetails(userDetails);
        Service service = new Service();
        service.clientService().updateUser(updateUserRequest);
    }
}
