package com.griddynamics.gridu.qa.user;

import com.griddynamics.gridu.qa.util.Parser;
import com.griddynamics.gridu.qa.util.Service;
import com.griddynamics.payment.qa.gridu.springsoap.gen.*;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.util.List;

public class CreateUserTest {

    @Test
    public void createUserTest() throws Exception{
        //Parser
        Parser parser = new Parser();
        CreateUserRequest createUserRequest = parser.parseJson();
//        CreateUserRequest createUserRequest = parser.parseFile();

        Service service = new Service();
        CreateUserResponse createUserResponse = service.clientService().createUser(createUserRequest);

        //Assertions
        //UserData
        SoftAssertions softly = new SoftAssertions();

//        //FieldByFieldWithIgnoringId
//        softly.assertThat(createUserResponse.getUserDetails())
//                .usingRecursiveComparison().ignoringExpectedNullFields()
//                .isEqualTo(createUserRequest);

        softly.assertThat(createUserResponse.getUserDetails().getName()).as("Name").isEqualTo(createUserRequest.getName());
        softly.assertThat(createUserResponse.getUserDetails().getLastName()).as("LastName").isEqualTo(createUserRequest.getLastName());
        softly.assertThat(createUserResponse.getUserDetails().getEmail()).as("Email").isEqualTo(createUserRequest.getEmail());
        softly.assertThat(createUserResponse.getUserDetails().getBirthday()).as("Birthday").isEqualTo(createUserRequest.getBirthday());

        //Addresses Assertions
        //Addresses and Payments List
        List<ExistingAddress> responseAddresses = createUserResponse.getUserDetails().getAddresses().getAddress();
        List<NewAddress> requestAddresses = createUserRequest.getAddresses().getAddress();
        List<ExistingPayment> responsePayments = createUserResponse.getUserDetails().getPayments().getPayment();
        List<NewPayment> requestPayments = createUserRequest.getPayments().getPayment();

        //Check Size
        softly.assertThat(responseAddresses.size()).as("AddressesSize").isEqualTo(requestAddresses.size());
        //Check Data
        for (int i = 0; i < requestAddresses.size(); i++) {
            softly.assertThat(responseAddresses.get(i).getState()).as("State").isEqualTo(requestAddresses.get(i).getState());
            softly.assertThat(responseAddresses.get(i).getCity()).as("City").isEqualTo(requestAddresses.get(i).getCity());
            softly.assertThat(responseAddresses.get(i).getZip()).as("Zip").isEqualTo(requestAddresses.get(i).getZip());
            softly.assertThat(responseAddresses.get(i).getLine1()).as("Line1").isEqualTo(requestAddresses.get(i).getLine1());
            softly.assertThat(responseAddresses.get(i).getLine2()).as("Line2").isEqualTo(requestAddresses.get(i).getLine2());

            //FieldByField
            //softly.assertThat(responseAddresses.get(i)).usingRecursiveComparison().isEqualTo(requestAddresses.get(i));

            //Check addressId isn't null
//            Assertions.assertNotNull(responseAddresses.get(i).getId(), "AddressId is null");
        }

        //Check Size
        //softly.assertThat(responsePayments.size()).as("PaymentsSize").isEqualTo(requestPayments.size());
        //Check Data
        for (int i = 0; i < requestPayments.size(); i++) {
            softly.assertThat(responsePayments.get(i).getCardNumber()).as("CardNumber").isEqualTo(requestPayments.get(i).getCardNumber());
            softly.assertThat(responsePayments.get(i).getExpiryYear()).as("ExpiryYear").isEqualTo(requestPayments.get(i).getExpiryYear());
            softly.assertThat(responsePayments.get(i).getExpiryMonth()).as("ExpiryMonth").isEqualTo(requestPayments.get(i).getExpiryMonth());
            softly.assertThat(responsePayments.get(i).getCardholder()).as("Cardholder").isEqualTo(requestPayments.get(i).getCardholder());
            softly.assertThat(responsePayments.get(i).getCvv()).as("Cvv").isEqualTo(requestPayments.get(i).getCvv());
//            //Check addressId isn't null
//            Assertions.assertNotNull(responsePayments.get(i).getId(), "PaymentId is null");
        }
        softly.assertAll();
    }
}
