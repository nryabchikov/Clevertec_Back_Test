package ru.clevertec.check.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static String url;
    private static String username;
    private static String password;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static void setDatabaseCredentials(String url, String username, String password) {
        DatabaseConnection.url = url;
        DatabaseConnection.username = username;
        DatabaseConnection.password = password;
    }
}
