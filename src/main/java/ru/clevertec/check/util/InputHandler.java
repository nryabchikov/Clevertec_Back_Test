package ru.clevertec.check.util;

import ru.clevertec.check.exception.DiscountCardNotFoundException;
import ru.clevertec.check.exception.PathToFileNotFoundException;
import ru.clevertec.check.exception.ProductLackOfQuantityException;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.service.ProductService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {
    private final Map<Integer, Integer> mapOfIdAndAmountOfProducts = new HashMap<>();
    private int numberOfDiscountCard;
    private int discountPercentage;
    private double balanceDebitCard;
    private boolean isBalanceDebitCardSet;
    private boolean isSaveToFilePathSet;
    private boolean isProductFilePathSet;
    private String productFilePath;
    private String saveFilePath = "result.csv";

    private static final Pattern DISCOUNT_CARD_PATTERN = Pattern.compile("discountCard=(\\d{4})");
    private static final Pattern BALANCE_DEBIT_CARD_PATTERN = Pattern.compile("balanceDebitCard=(\\d+(?:\\.\\d+)?)");
    private static final Pattern PRODUCT_PATTERN = Pattern.compile("(\\d+)-(\\d+)");
    private static final Pattern PRODUCT_FILE_PATH_PATTERN = Pattern.compile("pathToFile=([^\\s]+)");
    private static final Pattern SAVE_FILE_PATH_PATTERN = Pattern.compile("saveToFile=([^\\s]+)");

    public InputHandler(String[] args, String discountCardFilePath) throws IOException {
        parseArgs(args, discountCardFilePath);
        InputValidator.validateArgs(isBalanceDebitCardSet, mapOfIdAndAmountOfProducts.isEmpty(),
                isProductFilePathSet, isSaveToFilePathSet, saveFilePath);
    }

    private void parseArgs(String[] args, String discountCardFilePath) throws IOException {
        boolean isDone = false;
        Collections.reverse(Arrays.asList(args));
        for (String element : args) {
            Matcher discountCardMatcher = DISCOUNT_CARD_PATTERN.matcher(element);
            Matcher balanceDebitCardMatcher = BALANCE_DEBIT_CARD_PATTERN.matcher(element);
            Matcher productMatcher = PRODUCT_PATTERN.matcher(element);
            Matcher productFilePathMatcher = PRODUCT_FILE_PATH_PATTERN.matcher(element);
            Matcher saveFilePathMatcher = SAVE_FILE_PATH_PATTERN.matcher(element);

            if (discountCardMatcher.matches()) {
                handleDiscountCard(discountCardMatcher.group(1), discountCardFilePath);
            } else if (balanceDebitCardMatcher.matches()) {
                handleBalanceDebitCard(balanceDebitCardMatcher.group(1));
            } else if (productMatcher.matches()) {
                if (!isProductFilePathSet) {
                    CsvWriter.writeErrorToCsv(Status.BAD_REQUEST, saveFilePath);
                    throw new PathToFileNotFoundException("Path to file with products not provided.");
                }
                if (!isDone) {
                    CsvConverter.convert(productFilePath);
                    Path path = Paths.get(productFilePath);
                    String fileName = path.getFileName().toString();
                    productFilePath = productFilePath.substring(0, productFilePath.length() - fileName.length()) +
                            "converted_" + fileName;
                    isDone = true;
                }
                handleProduct(productMatcher.group(1), productMatcher.group(2));
            } else if (productFilePathMatcher.matches()) {
                isProductFilePathSet = true;
                handleProductFilePath(productFilePathMatcher.group(1));
            } else if (saveFilePathMatcher.matches()) {
                isSaveToFilePathSet = true;
                handleSaveFilePath(saveFilePathMatcher.group(1));
            }
        }
    }

    private void handleDiscountCard(String cardNumber, String discountCardFilePath) throws IOException {
        int cardId = Integer.parseInt(cardNumber);
        Map<Integer, Integer> discountCards = CsvReader.readDiscountCards(discountCardFilePath);
        if (!discountCards.containsKey(cardId)) {
            throw new DiscountCardNotFoundException("Discount card with number: " + cardNumber + " not found.");
        }
        numberOfDiscountCard = cardId;
        discountPercentage = discountCards.get(cardId);
    }

    private void handleBalanceDebitCard(String balance) {
        balanceDebitCard = Double.parseDouble(balance);
        isBalanceDebitCardSet = true;
    }

    private void handleProduct(String id, String amount) throws IOException {
        ProductService productService = new ProductService(productFilePath);
        int productId = Integer.parseInt(id);
        int quantity = Integer.parseInt(amount);
        Product product = productService.getProductById(productId);
        if (quantity > product.getQuantityInStock()) {
            throw new ProductLackOfQuantityException("The product with ID: " + id + " is not available in such quantity.");
        }
        mapOfIdAndAmountOfProducts.merge(productId, quantity, Integer::sum);
    }
    private void handleProductFilePath(String filePath) {
        this.productFilePath = filePath;
    }

    private void handleSaveFilePath(String filePath) {
        this.saveFilePath = filePath;
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

    public String getProductFilePath() {
        return productFilePath;
    }

    public String getSaveFilePath() {
        return saveFilePath;
    }
}
