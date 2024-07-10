package ru.clevertec.check.exception;

public class MissingProductsException extends RuntimeException {
    public MissingProductsException(String message) {
        super(message);
    }
}
