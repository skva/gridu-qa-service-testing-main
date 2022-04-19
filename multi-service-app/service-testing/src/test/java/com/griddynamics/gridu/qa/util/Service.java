package com.griddynamics.gridu.qa.util;

import com.griddynamics.payment.qa.gridu.springsoap.gen.UsersPort;

import javax.xml.namespace.QName;
import java.net.URL;

public class Service {
    public UsersPort clientService() throws Exception {
        URL wsdlURL = new URL("http://localhost:8080/ws/users.wsdl");
        QName SERVICE_NAME = new
                QName("http://gridu.qa.payment.griddynamics.com/springsoap/gen",
                "UsersPortService");
        javax.xml.ws.Service service = javax.xml.ws.Service.create(wsdlURL, SERVICE_NAME);
        UsersPort client = service.getPort(UsersPort.class);
        return client;
    }
}
