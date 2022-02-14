package com.griddynamics.gridu.qa.user;

import com.griddynamics.payment.qa.gridu.springsoap.gen.*;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

import static com.griddynamics.payment.qa.gridu.springsoap.gen.State.OR;


//IN PROGRESS
public class BaseTest {

    public UsersPort clientService() throws Exception{
        URL wsdlURL = new URL("http://localhost:8080/ws/users.wsdl");
        QName SERVICE_NAME = new
                QName("http://gridu.qa.payment.griddynamics.com/springsoap/gen",
                "UsersPortService");
        Service service = Service.create(wsdlURL, SERVICE_NAME);
        UsersPort client = service.getPort(UsersPort.class);
        return client;
    }

    public CreateUserRequest createUserRequest() {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setName("Jack111");
        userRequest.setLastName("Green");
        userRequest.setEmail("jackgreen@mail.com");
        return userRequest;
    }

    public NewAddress createNewAddress() {
        NewAddress newAddress = new NewAddress();
        newAddress.setState(OR);
        newAddress.setCity("Portland");
        newAddress.setZip("44444");
        newAddress.setLine1("Line1");
        newAddress.setLine2("Line222");
        return newAddress;
    }

    public ExistingAddress updateExistingAddress() {
        ExistingAddress existingAddress = new ExistingAddress();
        existingAddress.setId(42l);
        existingAddress.setState(OR);
        existingAddress.setCity("dsad");
        existingAddress.setZip("3333");
        existingAddress.setLine1("dsa");
        existingAddress.setLine2("dsad");
        return existingAddress;
    }

    public NewPayment createNewPayment() {
        NewPayment newPayment = new NewPayment();
        newPayment.setCardNumber("4444444444444444");
        newPayment.setExpiryYear(2222);
        newPayment.setExpiryMonth(12);
        newPayment.setCardholder("Jack White");
        newPayment.setCvv("444");
        return newPayment;
    }

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
