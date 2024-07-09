package ru.clevertec.check.util;

import ru.clevertec.check.exception.DatabaseCredentialsException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseInputHandler {
    private static final Pattern DATASOURCE_URL_PATTERN = Pattern.compile("datasource.url=(.+)");
    private static final Pattern DATASOURCE_USERNAME_PATTERN = Pattern.compile("datasource.username=(.+)");
    private static final Pattern DATASOURCE_PASSWORD_PATTERN = Pattern.compile("datasource.password=(.+)");

    public static void setDatabaseCredentials(String[] args) throws DatabaseCredentialsException {
        String url = null, username = null, password = null;
        for (String element : args) {
            Matcher datasourceUrlMatcher = DATASOURCE_URL_PATTERN.matcher(element);
            Matcher datasourceUsernameMatcher = DATASOURCE_USERNAME_PATTERN.matcher(element);
            Matcher datasourcePasswordMatcher = DATASOURCE_PASSWORD_PATTERN.matcher(element);

            if (datasourceUrlMatcher.matches()) {
                url = datasourceUrlMatcher.group(1);
            } else if (datasourceUsernameMatcher.matches()) {
                username = datasourceUsernameMatcher.group(1);
            } else if (datasourcePasswordMatcher.matches()) {
                password = datasourcePasswordMatcher.group(1);
            }
        }

        DatabaseConnection.setDatabaseCredentials(url, username, password);
        if (url == null || username == null || password == null) {
            throw new DatabaseCredentialsException("Database credentials are missing.");
        }
    }
}
