package main.java.ru.clevertec.check.exception;

public class PathToFileNotFoundException extends RuntimeException {
    public PathToFileNotFoundException(String message) {
        super(message);
    }
}
