package com.griddynamics.gridu.qa.util;

import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DataBaseUtil {
    @BeforeSuite
    public void dataSetup() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = getConnection()){
                Statement statement = conn.createStatement();
                statement.executeUpdate("TRUNCATE TABLE user");
                statement.executeUpdate("TRUNCATE TABLE payment");
                statement.executeUpdate("TRUNCATE TABLE address");
                int rows = statement.executeUpdate("INSERT User(id, birthday, email, last_name, name) " +
                        "VALUE (1, '2014-01-07', 'sql@mail.com', 'BlackSQL', 'JackSQL' )");
                System.out.printf("Added %d rows", rows);
                System.out.println("Connection to Store DB succesfull!");
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }
    public static Connection getConnection() throws SQLException, IOException {
        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("src/test/resources/database.properties"))){
            props.load(in);
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");
        return DriverManager.getConnection(url, username, password);
    }
}


//Old realization
//    @BeforeSuite
//    public void dataSetup() {
//        try{
//            String url = "jdbc:mysql://localhost:3306/service_testing_db?createDatabaseIfNotExist=true";
//            String username = "root";
//            String password = "12345678";
//            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
//            try (Connection conn = DriverManager.getConnection(url, username, password)){
//                Statement statement = conn.createStatement();
//                statement.executeUpdate("TRUNCATE TABLE user");
//                int rows = statement.executeUpdate("INSERT User(id, birthday, email, last_name, name) " +
//                        "VALUE (1, '2014-01-07', 'sql@mail.com', 'BlackSQL', 'JackSQL' )");
//                System.out.printf("Added %d rows", rows);
//            }
//        }
//        catch(Exception ex){
//            System.out.println("Connection failed...");
//            System.out.println(ex);
//        }
//    }



