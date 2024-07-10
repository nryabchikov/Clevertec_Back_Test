package ru.clevertec.check.exception;

public class SaveToFileNotFoundException extends RuntimeException {
    public SaveToFileNotFoundException(String message) {
        super(message);
    }
}
