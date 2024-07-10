package ru.clevertec.check.util;

public enum Status {
    BAD_REQUEST("BAD REQUEST"),
    NOT_ENOUGH_MONEY("NOT ENOUGH MONEY"),
    INTERNAL_SERVER_ERROR("INTERNAL SERVER ERROR");

    private final String message;

    Status(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
