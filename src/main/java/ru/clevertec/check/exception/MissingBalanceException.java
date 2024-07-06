package main.java.ru.clevertec.check.exception;

public class MissingBalanceException extends RuntimeException {
    public MissingBalanceException(String message) {
        super(message);
    }
}
