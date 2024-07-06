package main.java.ru.clevertec.check;

import main.java.ru.clevertec.check.model.Check;
import main.java.ru.clevertec.check.service.CheckService;
import main.java.ru.clevertec.check.util.CsvWriter;
import main.java.ru.clevertec.check.util.InputHandler;
import main.java.ru.clevertec.check.util.Status;

import java.io.IOException;

public class CheckRunner {
    public static void main(String[] args) throws IOException {
        final String OUTPUT_FILEPATH = "result.csv";
        final String PRODUCTS_FILEPATH = "src/main/resources/products.csv";
        final String DISCOUNT_CARDS_FILEPATH = "src/main/resources/discountCards.csv";
        InputHandler inputHandler = new InputHandler(args, PRODUCTS_FILEPATH, DISCOUNT_CARDS_FILEPATH);
        CheckService checkService = new CheckService(PRODUCTS_FILEPATH);
        Check check = checkService.generateCheck(
                inputHandler.getMapOfIdAndAmountOfProducts(),
                inputHandler.getNumberOfDiscountCard(),
                inputHandler.getDiscountPercentage(),
                inputHandler.getBalanceDebitCard()
        );
        if (inputHandler.getBalanceDebitCard() < check.getTotalPriceWithDiscount()) {
            CsvWriter.writeErrorToCsv(Status.NOT_ENOUGH_MONEY, OUTPUT_FILEPATH);
        } else {
            CsvWriter.writeCheckToCsv(check, OUTPUT_FILEPATH);
        }
    }
}

