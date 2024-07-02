package main.java.ru.clevertec.check.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CsvReader {
    private static final String SEPARATOR = ";";
    public static Map<Integer, String[]> readProducts(String filePath) throws IOException {
        Map<Integer, String[]> products = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] product = line.split(SEPARATOR);
                int id = Integer.parseInt(product[0]);
                products.put(id, product);
            }
        }
        return products;
    }

    public static Map<Integer, Integer> readDiscountCards(String filePath) throws IOException {
        Map<Integer, Integer> discountCards = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] card = line.split(SEPARATOR);
                int cardNumber = Integer.parseInt(card[1]);
                int discount = Integer.parseInt(card[2]);
                discountCards.put(cardNumber, discount);
            }
        }
        return discountCards;
    }
}

