package com.griddynamics.gridu.qa.user;

import com.griddynamics.gridu.qa.util.DataBaseUtil;
import com.griddynamics.gridu.qa.util.Parser;
import com.griddynamics.gridu.qa.util.Service;
import com.griddynamics.payment.qa.gridu.springsoap.gen.*;
import org.testng.annotations.Test;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserTest extends DataBaseUtil {

    @Test
    public void createUserTest() throws Exception{
        CreateUserRequest createUserRequest = Parser.parseJson("src/test/resources/createUser.json", CreateUserRequest.class);

        //CyTTER KOCTbIJIb
        XMLGregorianCalendar birthday = DatatypeFactory.newInstance()
                .newXMLGregorianCalendar("2014-01-07+04:00");
        createUserRequest.setBirthday(birthday);

        Service service = new Service();
        CreateUserResponse createUserResponse = service.clientService().createUser(createUserRequest);

        //FieldByFieldWithIgnoringId
        assertThat(createUserResponse.getUserDetails())
                .usingRecursiveComparison().ignoringExpectedNullFields()
                .isEqualTo(createUserRequest);
    }
}
