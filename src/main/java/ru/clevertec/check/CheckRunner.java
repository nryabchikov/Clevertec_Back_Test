package ru.clevertec.check;

import ru.clevertec.check.exception.*;
import ru.clevertec.check.model.Check;
import ru.clevertec.check.repository.CheckRepository;
import ru.clevertec.check.repository.ProductRepository;
import ru.clevertec.check.service.CheckService;
import ru.clevertec.check.service.ProductService;
import ru.clevertec.check.util.CsvWriter;
import ru.clevertec.check.util.InputHandler;
import ru.clevertec.check.util.Status;

import java.io.IOException;

public class CheckRunner {
    public static void main(String[] args) throws IOException {
        args = new String[]{"3-1", "2-5", "5-1", "discountCard=1111", "balanceDebitCard=100", "saveToFile=result.csv",
        "datasource.url=jdbc:postgresql://localhost:5432/clevertec_db", "datasource.username=nikitaryabchikov", "datasource.password=123"};
        String defaultSaveFilePath = "result.csv";
        try {
            InputHandler inputHandler = new InputHandler(args);
            defaultSaveFilePath = inputHandler.getSaveFilePath();
            CheckService checkService = new CheckService(new CheckRepository(new ProductService(new ProductRepository())));
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
