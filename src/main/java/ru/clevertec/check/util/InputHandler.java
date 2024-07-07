package main.java.ru.clevertec.check.util;

import main.java.ru.clevertec.check.exception.DiscountCardNotFoundException;
import main.java.ru.clevertec.check.exception.ProductLackOfQuantityException;
import main.java.ru.clevertec.check.model.Product;
import main.java.ru.clevertec.check.service.ProductService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {
    private final Map<Integer, Integer> mapOfIdAndAmountOfProducts = new HashMap<>();
    private int numberOfDiscountCard;
    private int discountPercentage;
    private double balanceDebitCard;
    private boolean isBalanceDebitCardSet = false;

    private static final Pattern DISCOUNT_CARD_PATTERN = Pattern.compile("discountCard=(\\d{4})");
    private static final Pattern BALANCE_DEBIT_CARD_PATTERN = Pattern.compile("balanceDebitCard=(\\d+(?:\\.\\d+)?)");
    private static final Pattern PRODUCT_PATTERN = Pattern.compile("(\\d+)-(\\d+)");

    public InputHandler(String[] args, String productFilePath, String discountCardFilePath) throws IOException {
        parseArgs(args, productFilePath, discountCardFilePath);
        InputValidator.validateArgs(isBalanceDebitCardSet, mapOfIdAndAmountOfProducts.isEmpty());
    }

    private void parseArgs(String[] args, String productFilePath, String discountCardFilePath) throws IOException {
        ProductService productService = new ProductService(productFilePath);
        final String OUTPUT_FILE_PATH = "result.csv";

        for (String element : args) {
            Matcher discountCardMatcher = DISCOUNT_CARD_PATTERN.matcher(element);
            Matcher balanceDebitCardMatcher = BALANCE_DEBIT_CARD_PATTERN.matcher(element);
            Matcher productMatcher = PRODUCT_PATTERN.matcher(element);

            if (discountCardMatcher.matches()) {
                handleDiscountCard(discountCardMatcher.group(1), discountCardFilePath, OUTPUT_FILE_PATH);
            } else if (balanceDebitCardMatcher.matches()) {
                handleBalanceDebitCard(balanceDebitCardMatcher.group(1));
            } else if (productMatcher.matches()) {
                handleProduct(productMatcher.group(1), productMatcher.group(2), productService, OUTPUT_FILE_PATH);
            }
        }
    }

    private void handleDiscountCard(String cardNumber, String discountCardFilePath, String outputFilePath) throws IOException {
        int cardId = Integer.parseInt(cardNumber);
        Map<Integer, Integer> discountCards = CsvReader.readDiscountCards(discountCardFilePath);
        if (!discountCards.containsKey(cardId)) {
            CsvWriter.writeErrorToCsv(Status.BAD_REQUEST, outputFilePath);
            throw new DiscountCardNotFoundException("Discount card with number: " + cardNumber + " not found.");
        }
        numberOfDiscountCard = cardId;
        discountPercentage = discountCards.get(cardId);
    }

    private void handleBalanceDebitCard(String balance) {
        balanceDebitCard = Double.parseDouble(balance);
        isBalanceDebitCardSet = true;
    }

    private void handleProduct(String id, String amount, ProductService productService, String outputFilePath) throws IOException {
        int productId = Integer.parseInt(id);
        int quantity = Integer.parseInt(amount);
        Product product = productService.getProductById(productId);
        if (quantity > product.getQuantityInStock()) {
            CsvWriter.writeErrorToCsv(Status.BAD_REQUEST, outputFilePath);
            throw new ProductLackOfQuantityException("The product with ID: " + id + " is not available in such quantity.");
        }
        mapOfIdAndAmountOfProducts.merge(productId, quantity, Integer::sum);
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
