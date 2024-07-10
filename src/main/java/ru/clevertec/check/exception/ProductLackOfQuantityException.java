package ru.clevertec.check.exception;

public class ProductLackOfQuantityException extends RuntimeException {
    public ProductLackOfQuantityException(String message) {
        super(message);
    }
}
