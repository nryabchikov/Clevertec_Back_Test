package main.java.ru.clevertec.check;

import main.java.ru.clevertec.check.exception.*;
import main.java.ru.clevertec.check.model.Check;
import main.java.ru.clevertec.check.service.CheckService;
import main.java.ru.clevertec.check.util.CsvWriter;
import main.java.ru.clevertec.check.util.InputHandler;
import main.java.ru.clevertec.check.util.Status;

import java.io.IOException;

public class CheckRunner {
    public static void main(String[] args) throws IOException {
        final String DISCOUNT_CARDS_FILEPATH = "src/main/resources/discountCards.csv";
        String defaultSaveFilePath = "result.csv";
        try {
            InputHandler inputHandler = new InputHandler(args, DISCOUNT_CARDS_FILEPATH);
            defaultSaveFilePath = inputHandler.getSaveFilePath();
            CheckService checkService = new CheckService(inputHandler.getProductFilePath());
            Check check = checkService.generateCheck(
                    inputHandler.getMapOfIdAndAmountOfProducts(),
                    inputHandler.getNumberOfDiscountCard(),
                    inputHandler.getDiscountPercentage(),
                    inputHandler.getBalanceDebitCard()
            );
            if (inputHandler.getBalanceDebitCard() < check.getTotalPriceWithDiscount()) {
                System.err.println(Status.NOT_ENOUGH_MONEY.getMessage());
                CsvWriter.writeErrorToCsv(Status.NOT_ENOUGH_MONEY, inputHandler.getSaveFilePath());
            } else {
                CsvWriter.writeCheckToCsv(check, inputHandler.getSaveFilePath());
            }
        } catch (DiscountCardNotFoundException | MissingBalanceException | MissingProductsException |
                 ProductLackOfQuantityException | PathToFileNotFoundException | SaveToFileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            CsvWriter.writeErrorToCsv(Status.INTERNAL_SERVER_ERROR, defaultSaveFilePath);
        }
    }
}
