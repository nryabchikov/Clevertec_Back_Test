package ru.clevertec.check.util;

import ru.clevertec.check.exception.MissingBalanceException;
import ru.clevertec.check.exception.MissingProductsException;
import ru.clevertec.check.exception.PathToFileNotFoundException;
import ru.clevertec.check.exception.SaveToFileNotFoundException;

import java.io.IOException;

public class InputValidator {
     static void validateArgs(boolean isBalanceDebitCardSet, boolean isEmptyMap, boolean isProductFilePathSet,
                              boolean isSaveToFilePathSet, String OUTPUT_FILE_PATH) throws IOException {
        if (!isBalanceDebitCardSet) {
            CsvWriter.writeErrorToCsv(Status.BAD_REQUEST, OUTPUT_FILE_PATH);
            throw new MissingBalanceException("Balance debit card not provided.");
        }

        if (isEmptyMap) {
            CsvWriter.writeErrorToCsv(Status.BAD_REQUEST, OUTPUT_FILE_PATH);
            throw new MissingProductsException("No products provided.");
        }

        if (!isProductFilePathSet) {
            CsvWriter.writeErrorToCsv(Status.BAD_REQUEST, OUTPUT_FILE_PATH);
            throw new PathToFileNotFoundException("Path to file with products not provided.");
        }

        if (!isSaveToFilePathSet) {
            CsvWriter.writeErrorToCsv(Status.BAD_REQUEST, OUTPUT_FILE_PATH);
            throw new SaveToFileNotFoundException("Path to file to save not provided.");
        }
    }
}
