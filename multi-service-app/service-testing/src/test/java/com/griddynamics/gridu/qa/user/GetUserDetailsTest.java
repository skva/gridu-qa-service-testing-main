package com.griddynamics.gridu.qa.user;

import com.griddynamics.gridu.qa.util.Service;
import com.griddynamics.payment.qa.gridu.springsoap.gen.GetUserDetailsRequest;
import com.griddynamics.payment.qa.gridu.springsoap.gen.GetUserDetailsResponse;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class GetUserDetailsTest {


    public void TableFill() {
        try{
            String url = "jdbc:mysql://localhost:3306/service_testing_db?createDatabaseIfNotExist=true";
            String username = "root";
            String password = "12345678";
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                Statement statement = conn.createStatement();
                statement.executeUpdate("TRUNCATE TABLE user");
                int rows = statement.executeUpdate("INSERT User(id, birthday, email, last_name, name) " +
                        "VALUE (1, '2014-01-07', 'sql@mail.com', 'BlackSQL', 'JackSQL' )");
                System.out.printf("Added %d rows", rows);
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

    @Test
    public void getUserDetailsTest() throws Exception {
        GetUserDetailsRequest getUserDetailsRequest = new GetUserDetailsRequest();
        getUserDetailsRequest.setUserId(1);

        Service service = new Service();
        GetUserDetailsResponse getUserDetailsResponse = service.clientService().getUserDetails(getUserDetailsRequest);

        System.out.println(getUserDetailsResponse.getUserDetails().getId());
        //Assert GET not_found
    }
}
