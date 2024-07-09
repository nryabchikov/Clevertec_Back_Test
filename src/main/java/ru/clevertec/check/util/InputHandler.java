package ru.clevertec.check.util;

import ru.clevertec.check.exception.DatabaseCredentialsException;
import ru.clevertec.check.exception.DiscountCardNotFoundException;
import ru.clevertec.check.exception.MissingProductsException;
import ru.clevertec.check.exception.ProductLackOfQuantityException;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.repository.DiscountCardRepository;
import ru.clevertec.check.repository.ProductRepository;
import ru.clevertec.check.service.DiscountCardService;
import ru.clevertec.check.service.ProductService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {
    private final Map<Integer, Integer> mapOfIdAndAmountOfProducts = new HashMap<>();
    private int numberOfDiscountCard;
    private int discountPercentage;
    private double balanceDebitCard;
    private boolean isBalanceDebitCardSet;
    private boolean isSaveFilePathSet;
    private String saveFilePath = "result.csv";

    private static final Pattern DISCOUNT_CARD_PATTERN = Pattern.compile("discountCard=(\\d{4})");
    private static final Pattern BALANCE_DEBIT_CARD_PATTERN = Pattern.compile("balanceDebitCard=(-?\\d+(?:\\.\\d+)?)");
    private static final Pattern PRODUCT_PATTERN = Pattern.compile("(\\d+)-(\\d+)");
    private static final Pattern SAVE_FILE_PATH_PATTERN = Pattern.compile("saveToFile=(.+)");


    private final ProductService productService;
    private final DiscountCardService discountCardService;

    public String getSaveFilePath() {
        return saveFilePath;
    }

    public InputHandler(String[] args) throws SQLException, IOException {
        this.productService = new ProductService(new ProductRepository());
        this.discountCardService = new DiscountCardService(new DiscountCardRepository());
        DatabaseInputHandler.setDatabaseCredentials(args);
        parseArgs(args);
        InputValidator.validateArgs(isBalanceDebitCardSet, mapOfIdAndAmountOfProducts.isEmpty(),
                isSaveFilePathSet, saveFilePath);
    }

    void parseArgs(String[] args) throws SQLException {
        Collections.reverse(Arrays.asList(args));
        for (String element : args) {
            Matcher saveFilePathMatcher = SAVE_FILE_PATH_PATTERN.matcher(element);
            Matcher discountCardMatcher = DISCOUNT_CARD_PATTERN.matcher(element);
            Matcher balanceDebitCardMatcher = BALANCE_DEBIT_CARD_PATTERN.matcher(element);
            Matcher productMatcher = PRODUCT_PATTERN.matcher(element);
            if (saveFilePathMatcher.matches()) {
                isSaveFilePathSet = true;
                saveFilePath = saveFilePathMatcher.group(1);
            } else if (discountCardMatcher.matches()) {
                handleDiscountCard(discountCardMatcher.group(1));
            } else if (balanceDebitCardMatcher.matches()) {
                handleBalanceDebitCard(balanceDebitCardMatcher.group(1));
            } else if (productMatcher.matches()) {
                handleProduct(productMatcher.group(1), productMatcher.group(2));
            }
        }
    }

    void handleDiscountCard(String cardNumber) throws SQLException {
        int cardId = Integer.parseInt(cardNumber);
        Map<Integer, Integer> discountCards = discountCardService.getAllDiscountCards();
        if (!discountCards.containsKey(cardId)) {
            throw new DiscountCardNotFoundException("Discount card with number: " + cardNumber + " not found.");
        }
        numberOfDiscountCard = cardId;
        discountPercentage = discountCards.get(cardId);
    }

    void handleBalanceDebitCard(String balance) {
        balanceDebitCard = Double.parseDouble(balance);
        isBalanceDebitCardSet = true;
    }

    void handleProduct(String id, String amount) throws SQLException {
        int productId = Integer.parseInt(id);
        int quantity = Integer.parseInt(amount);
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new MissingProductsException("There is no such product with ID: " + id + ".");
        }
        if (quantity > product.getQuantityInStock()) {
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
