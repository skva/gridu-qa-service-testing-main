package com.griddynamics.gridu.qa.util;


import com.fasterxml.jackson.databind.SerializationFeature;
import com.griddynamics.payment.qa.gridu.springsoap.gen.CreateUserRequest;
import com.griddynamics.payment.qa.gridu.springsoap.gen.NewAddress;
import com.griddynamics.payment.qa.gridu.springsoap.gen.NewPayment;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static com.griddynamics.payment.qa.gridu.springsoap.gen.State.OR;

public class Parser {
    public CreateUserRequest parseJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        mapper.setDateFormat(df);


        CreateUserRequest createUserRequest = mapper.readValue
                (new File("src/test/resources/createUser.json"), CreateUserRequest.class);
        System.out.println(createUserRequest.getBirthday());
        return createUserRequest;
    }




    //try argument method with arg 'file'
    public CreateUserRequest parseFile() throws Exception {
        File file = new File("src/test/resources/createUser.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document xml = dbf.newDocumentBuilder().parse(file);

        //XMLGregorianCalendar birthday
        XMLGregorianCalendar birthday = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar(xml.getElementsByTagName("birthday").item(0).getTextContent());

        //Address
        CreateUserRequest.Addresses address = new CreateUserRequest.Addresses();
        NewAddress newAddress = new NewAddress();
        newAddress.setState(OR); //Required State instead String
        newAddress.setCity(xml.getElementsByTagName("city").item(0).getTextContent());
        newAddress.setZip(xml.getElementsByTagName("zip").item(0).getTextContent());
        newAddress.setLine1(xml.getElementsByTagName("line1").item(0).getTextContent());
        newAddress.setLine2(xml.getElementsByTagName("line2").item(0).getTextContent());
        address.getAddress().add(newAddress);

        //Payment
        CreateUserRequest.Payments payment = new CreateUserRequest.Payments();
        NewPayment newPayment = new NewPayment();
        newPayment.setCardNumber(xml.getElementsByTagName("cardNumber").item(0).getTextContent());
        newPayment.setExpiryYear(2222); //Required int instead String
        newPayment.setExpiryMonth(12); //Required int instead String
        newPayment.setCardholder(xml.getElementsByTagName("cardholder").item(0).getTextContent());
        newPayment.setCvv(xml.getElementsByTagName("cvv").item(0).getTextContent());
        payment.getPayment().add(newPayment);

        //New user with Address and Payment
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setName(xml.getElementsByTagName("name").item(0).getTextContent());
        createUserRequest.setLastName(xml.getElementsByTagName("lastName").item(0).getTextContent());
        createUserRequest.setEmail(xml.getElementsByTagName("email").item(0).getTextContent());
        createUserRequest.setBirthday(birthday);
        createUserRequest.setAddresses(address);
        createUserRequest.setPayments(payment);

        return createUserRequest;
    }

    public Document parseFileU() throws Exception {
        File file = new File("src/test/resources/updateUser.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document xml = dbf.newDocumentBuilder().parse(file);
        return xml;
    }

    public void testParseXml() {
        try {
            File file = new File("src/test/resources/createUser.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(CreateUserRequest.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            CreateUserRequest createUserRequest = (CreateUserRequest) unmarshaller.unmarshal(file);
            System.out.println(createUserRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
