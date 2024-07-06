package main.java.ru.clevertec.check;

import main.java.ru.clevertec.check.exception.DiscountCardNotFoundException;
import main.java.ru.clevertec.check.exception.MissingBalanceException;
import main.java.ru.clevertec.check.exception.MissingProductsException;
import main.java.ru.clevertec.check.exception.ProductLackOfQuantityException;
import main.java.ru.clevertec.check.model.Check;
import main.java.ru.clevertec.check.service.CheckService;
import main.java.ru.clevertec.check.util.CsvWriter;
import main.java.ru.clevertec.check.util.InputHandler;
import main.java.ru.clevertec.check.util.Status;

import java.io.IOException;

public class CheckRunner {
    public static void main(String[] args) throws IOException {
        final String OUTPUT_FILE_PATH = "result.csv";
        final String PRODUCTS_FILEPATH = "src/main/resources/products.csv";
        final String DISCOUNT_CARDS_FILEPATH = "src/main/resources/discountCards.csv";
        try {
            InputHandler inputHandler = new InputHandler(args, PRODUCTS_FILEPATH, DISCOUNT_CARDS_FILEPATH);
            CheckService checkService = new CheckService(PRODUCTS_FILEPATH);
            Check check = checkService.generateCheck(
                    inputHandler.getMapOfIdAndAmountOfProducts(),
                    inputHandler.getNumberOfDiscountCard(),
                    inputHandler.getDiscountPercentage(),
                    inputHandler.getBalanceDebitCard()
            );
            if (inputHandler.getBalanceDebitCard() < check.getTotalPriceWithDiscount()) {
                System.err.println(Status.NOT_ENOUGH_MONEY.getMessage());
                CsvWriter.writeErrorToCsv(Status.NOT_ENOUGH_MONEY, OUTPUT_FILE_PATH);
            } else {
                CsvWriter.writeCheckToCsv(check, OUTPUT_FILE_PATH);
            }
        } catch (DiscountCardNotFoundException | MissingBalanceException | MissingProductsException |
                 ProductLackOfQuantityException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            CsvWriter.writeErrorToCsv(Status.INTERNAL_SERVER_ERROR, OUTPUT_FILE_PATH);
        }
    }
}

