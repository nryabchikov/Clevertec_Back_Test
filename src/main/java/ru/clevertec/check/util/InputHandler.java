package main.java.ru.clevertec.check.util;

import main.java.ru.clevertec.check.exception.DiscountCardNotFoundException;
import main.java.ru.clevertec.check.exception.MissingBalanceException;
import main.java.ru.clevertec.check.exception.MissingProductsException;
import main.java.ru.clevertec.check.exception.ProductLackOfQuantityException;
import main.java.ru.clevertec.check.model.Product;
import main.java.ru.clevertec.check.service.ProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InputHandler {
    private final Map<Integer, Integer> mapOfIdAndAmountOfProducts = new HashMap<>();
    private int numberOfDiscountCard;
    private int discountPercentage;
    private double balanceDebitCard;
    private boolean isBalanceDebitCardSet = false;

    private void setArgs(String[] args, String productFilePath, String discountCardFilePath) throws IOException {
        ProductService productService = new ProductService(productFilePath);
        final String OUTPUT_FILE_PATH = "result.csv";
        for (String element: args) {
            if (element.contains("discountCard=")) {
                String numberOfCard = element.substring("discountCard=".length());
                Map<Integer, Integer> discountCards = CsvReader.readDiscountCards(discountCardFilePath);
                if (!(discountCards.containsKey(Integer.parseInt(numberOfCard)))) {
                    CsvWriter.writeErrorToCsv(Status.BAD_REQUEST, OUTPUT_FILE_PATH);
                    throw new DiscountCardNotFoundException("Discount card with number: " + numberOfCard + " not found.");
                }
                numberOfDiscountCard = Integer.parseInt(numberOfCard);
                discountPercentage = discountCards.get(Integer.parseInt(numberOfCard));
            } else if (element.contains("balanceDebitCard=")) {
                balanceDebitCard = Double.parseDouble(element.substring("balanceDebitCard=".length()));
                isBalanceDebitCardSet = true;
            } else if (element.contains("-")) {
                String[] parts = element.split("-");
                int id = Integer.parseInt(parts[0]);
                int amount = Integer.parseInt(parts[1]);
                Product product = productService.getProductById(id);
                if (amount > product.getQuantityInStock()) {
                    CsvWriter.writeErrorToCsv(Status.BAD_REQUEST, OUTPUT_FILE_PATH);
                    throw new ProductLackOfQuantityException("The product with ID: " +
                            id + " is not available in such quantity.");
                }
                if (mapOfIdAndAmountOfProducts.containsKey(id)) {
                    mapOfIdAndAmountOfProducts.put(id, mapOfIdAndAmountOfProducts.get(id) + amount);
                } else {
                    mapOfIdAndAmountOfProducts.put(id, amount);
                }
            } else {

            }
        }

        if (!isBalanceDebitCardSet) {
            CsvWriter.writeErrorToCsv(Status.BAD_REQUEST, OUTPUT_FILE_PATH);
            throw new MissingBalanceException("Balance debit card not provided.");
        }

        if (mapOfIdAndAmountOfProducts.isEmpty()) {
            CsvWriter.writeErrorToCsv(Status.BAD_REQUEST, OUTPUT_FILE_PATH);
            throw new MissingProductsException("No products provided.");
        }
    }
    public InputHandler(String[] args, String productFilePath, String discountCardFilePath) throws IOException {
        setArgs(args, productFilePath, discountCardFilePath);
    }

    public Map<Integer, Integer> getMapOfIdAndAmountOfProducts() {
        return mapOfIdAndAmountOfProducts;
    }
    public int getNumberOfDiscountCard() {
        return numberOfDiscountCard;
    }
    public int getDiscountPercentage() {
        return discountPercentage;
    }
    public double getBalanceDebitCard() {
        return balanceDebitCard;
    }
}
