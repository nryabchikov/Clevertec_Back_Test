package ru.clevertec.check.util;

import javax.servlet.ServletException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseProperties {
    public static void initDB() throws ServletException {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(System.getProperty("catalina.base") + "/conf/catalina.properties"));

            String url = properties.getProperty("datasource.url");
            String username = properties.getProperty("datasource.username");
            String password = properties.getProperty("datasource.password");
            DatabaseConnection.setDatabaseCredentials(url, username, password);
            if (url == null) {
                throw new SQLException("The url cannot be null");
            }
        } catch (SQLException | IOException e) {
            throw new ServletException("Unable to initialize DiscountCardServlet.", e);
        }
    }
}
