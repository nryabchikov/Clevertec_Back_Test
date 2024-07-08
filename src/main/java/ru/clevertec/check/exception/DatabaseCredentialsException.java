package ru.clevertec.check.exception;

public class DatabaseCredentialsException extends RuntimeException {
    public DatabaseCredentialsException(String message) {
        super(message);
    }
}
