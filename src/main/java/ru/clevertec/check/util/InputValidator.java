package ru.clevertec.check.util;

import ru.clevertec.check.exception.MissingBalanceException;
import ru.clevertec.check.exception.MissingProductsException;

import java.io.IOException;

public class InputValidator {
     static void validateArgs(boolean isBalanceDebitCardSet, boolean isEmptyMap) throws IOException {
        final String OUTPUT_FILE_PATH = "result.csv";
        if (!isBalanceDebitCardSet) {
            CsvWriter.writeErrorToCsv(Status.BAD_REQUEST, OUTPUT_FILE_PATH);
            throw new MissingBalanceException("Balance debit card not provided.");
        }

        if (isEmptyMap) {
            CsvWriter.writeErrorToCsv(Status.BAD_REQUEST, OUTPUT_FILE_PATH);
            throw new MissingProductsException("No products provided.");
        }
    }
}
