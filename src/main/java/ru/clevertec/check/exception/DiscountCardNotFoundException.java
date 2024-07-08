package ru.clevertec.check.exception;

public class DiscountCardNotFoundException extends RuntimeException {
    public DiscountCardNotFoundException(String message) {
        super(message);
    }
}
