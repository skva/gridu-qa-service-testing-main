package com.griddynamics.gridu.qa.util;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.testng.annotations.*;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

public class Util {

    //WireMock
    private static final String HOST = "localhost";
    private static final int PORT = 9091;
    private static WireMockServer server = new WireMockServer(PORT);

//    public String wiremockUrl = getPropertyValue("wiremock.url");
//    public String paymentPort = getPropertyValue("wiremock.payment.port");
//    public String addressPort = getPropertyValue("wiremock.address.port");
//    public String gatewayPort = getPropertyValue("wiremock.gateway.port");

    public void initWiremock() {
        System.out.println("InitWiremock");

        server.start();
        configureFor(HOST, PORT);

        // PM stub
        WireMock.stubFor(WireMock.any(WireMock.urlPathMatching("/payment/.*"))
                .willReturn(WireMock.aResponse().proxiedFrom(Util.getWiremockPaymentURL())));

                // AM stub
        WireMock.stubFor(WireMock.any(WireMock.urlPathMatching("/address/.*"))
                .willReturn(WireMock.aResponse().proxiedFrom(Util.getWiremockAddressURL())));

        // Gateway stub
        WireMock.stubFor(WireMock.any(WireMock.urlPathMatching("/card/verify"))
                .willReturn(WireMock.aResponse().proxiedFrom(Util.getWiremockGatewayURL())));

//        // PM stub
//        WireMock.stubFor(WireMock.any(WireMock.urlPathMatching("/payment/.*"))
//                .willReturn(WireMock.aResponse().proxiedFrom(wiremockUrl + paymentPort)));
//
//        // AM stub
//        WireMock.stubFor(WireMock.any(WireMock.urlPathMatching("/address/.*"))
//                .willReturn(WireMock.aResponse().proxiedFrom(wiremockUrl + addressPort)));
//
//        // Gateway stub
//        WireMock.stubFor(WireMock.any(WireMock.urlPathMatching("/card/verify"))
//                .willReturn(WireMock.aResponse().proxiedFrom(wiremockUrl + gatewayPort)));
    }

    //Tables
    private final static String USER = "user";
    private final static String ADDRESS = "address";
    private final static String PAYMENT = "payment";

    Sql2o sql2o = new Sql2o(dataSource());

    @BeforeMethod
    public void prepareEnvironment() {
        cleanDB();
        fillUserTable();
        fillAddressTable();
        fillPaymentTable();

        initWiremock();
    }

    public void fillUser(int id, String birthday, String email, String last_name, String name) {
        try (org.sql2o.Connection connection = sql2o.open()) {
            Query query =
                    connection.createQuery("insert into user(id, birthday, email, last_name, name)"
            + "values ("+ id +", "+ birthday +", "+ email +", "+ last_name +", "+ name +" )");
            query.executeUpdate().getResult();
        }
    }

    public void fillUserTable() {
        fillUser(1, "'2014-01-01'", "'sql@mail1com'", "'White1'", "'Jack1'");
        fillUser(2, "'2014-01-02'", "'sql@mail2.com'", "'White2'", "'Jack2'");
        fillUser(3, "'2014-01-03'", "'sql@mail3.com'", "'White3'", "'Jack3'");
        fillUser(4, "'2014-01-04'", "'sql@mail4.com'", "'White4'", "'Jack4'");
        fillUser(5, "'2014-01-05'", "'sql@mail5.com'", "'White5'", "'Jack5'");
    }

    public void fillAddress(int id, String state, String city, String zip, String line_one, String line_two, int user_id) {
        try (org.sql2o.Connection connection = sql2o.open()) {
            Query query =
                    connection.createQuery("insert into address(id, state, city, zip, line_one, line_two, user_id)"
                            + "values (" + id +", " + state +", "+ city +", "+ zip +", "+ line_one +", "+ line_two +", " + user_id +" )");
            query.executeUpdate().getResult();
        }
    }

    public void fillAddressTable() {
        fillAddress(1,"'OR'", "'Portland'", "'55555'", "'Line1'", "'Line2'", 1);
        fillAddress(2,"'OR'", "'Portland'", "'55555'", "'Line1'", "'Line2'", 2);
        fillAddress(3,"'OR'", "'Portland'", "'55555'", "'Line1'", "'Line2'", 3);
        fillAddress(4,"'OR'", "'Portland'", "'55555'", "'Line1'", "'Line2'", 4);
    }

    public void fillPayment(int id, int card_number, String cardholder, int cvv, int expiry_month, int expiry_year, String token, int user_id) {
        try (org.sql2o.Connection connection = sql2o.open()) {
            Query query =
                    connection.createQuery("insert into payment(id, card_number, cardholder, cvv, expiry_month, expiry_year, token, user_id)"
                            + "values (" + id +", " + card_number +", "+ cardholder +", "+ cvv +", "+ expiry_month +", "+ expiry_year +", "+ token +", " + user_id +" )");
            query.executeUpdate().getResult();
        }
    }

    public void fillPaymentTable() {
        fillPayment(1, 444, "'Four'", 444, 4, 4, "'someCardTokenReturnedHere'", 1);
        fillPayment(2, 444, "'Four'", 444, 4, 4, "'someCardTokenReturnedHere'", 2);
        fillPayment(3, 444, "'Four'", 444, 4, 4, "'someCardTokenReturnedHere'", 3);
        fillPayment(4, 444, "'Four'", 444, 4, 4, "'someCardTokenReturnedHere'", 4);
    }

    public void cleanTables(String table) {
        try (org.sql2o.Connection connection = sql2o.open()) {
            Query cleanTable = connection.createQuery("TRUNCATE TABLE " + table);
            cleanTable.executeUpdate().getResult();
        }
    }

    public void cleanDB() {
        cleanTables(USER);
        cleanTables(ADDRESS);
        cleanTables(PAYMENT);
    }

    public static DataSource dataSource() {
        Properties props = new Properties();
        FileInputStream fis = null;
        MysqlDataSource mysqlDS = null;
        try {
            fis = new FileInputStream("src/test/resources/database.properties");
            props.load(fis);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mysqlDS;
    }



    // Config
    private static final String PATH = "wiremock.properties";
    private static final Properties config = new Properties();

    public static String getWiremockPaymentURL() {
        return String.format("%s://%s:%s/", getWiremockScheme(), getWiremockHost(), getWiremockPaymentPort());
    }

    public static String getWiremockAddressURL() {
        return String.format("%s://%s:%s/", getWiremockScheme(), getWiremockHost(), getWiremockAddressPort());
    }

    public static String getWiremockGatewayURL() {
        return String.format("%s://%s:%s/", getWiremockScheme(), getWiremockHost(), getWiremockGatewayPort());
    }

    public static String getWiremockScheme() {
        return config.getProperty("wiremock.scheme");
    }

    public static String getWiremockHost() {
        return config.getProperty("wiremock.host");
    }

    public static int getWiremockPaymentPort() {
        return Integer.parseInt(config.getProperty("wiremock.payment.port"));
    }

    public static int getWiremockAddressPort() {
        return Integer.parseInt(config.getProperty("wiremock.address.port"));
    }

    public static int getWiremockGatewayPort() {
        return Integer.parseInt(config.getProperty("wiremock.gateway.port"));
    }

    static {
        InputStream in = null;
        try {
            in = Util.class.getClassLoader().getResourceAsStream(PATH);
            if (in == null) {
                in = new FileInputStream(PATH);
            }
            config.load(in);
        } catch (Throwable e) {
            String msg = "Can't initialize configuration properties bundle.";
            throw new RuntimeException(msg, e);
        }
    }

//
//    public String getPropertyValue(String propertyName) {
//        String propertyValue = "";
//        FileInputStream fis = null;
//        Properties properties = new Properties();
//
//        try {
//            fis = new FileInputStream("src/test/resources/wiremock.properties");
//            properties.load(fis);
//            propertyValue = properties.getProperty(propertyName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return propertyValue;
//    }
}


//Old realization
//@BeforeSuite
//public void dataSetup() {
//    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//    try{
//        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
//        try (Connection conn = getConnection()){
//            Statement statement = conn.createStatement();
//            statement.executeUpdate("TRUNCATE TABLE user");
//            statement.executeUpdate("TRUNCATE TABLE payment");
//            statement.executeUpdate("TRUNCATE TABLE address");
//            int rows = statement.executeUpdate("INSERT User(id, birthday, email, last_name, name) " +
//                    "VALUE (1, '2014-01-07', 'sql@mail.com', 'BlackSQL', 'JackSQL' )");
//            System.out.printf("Added %d rows", rows);
//            System.out.println("Connection to Store DB succesfull!");
//        }
//    }
//    catch(Exception ex){
//        System.out.println("Connection failed...");
//        System.out.println(ex);
//    }
//}
//    public static Connection getConnection() throws SQLException, IOException {
//        Properties props = new Properties();
//        try(InputStream in = Files.newInputStream(Paths.get("src/test/resources/database.properties"))){
//            props.load(in);
//        }
//        String url = props.getProperty("url");
//        String username = props.getProperty("username");
//        String password = props.getProperty("password");
//        return DriverManager.getConnection(url, username, password);
//    }




//Oldest realization
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



