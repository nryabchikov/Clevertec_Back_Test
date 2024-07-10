package ru.clevertec.check.util;

import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Product;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class JsonParser {
    public static Product parseProductFromRequest(HttpServletRequest req) throws IOException {
        BufferedReader reader = req.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        String json = jsonBuilder.toString();
        Product product = new Product();

        json = json.replace("{", "").replace("}", "").replace("\"", "");
        String[] fields = json.split(",");
        for (String field : fields) {
            String[] keyValue = field.split(":");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            switch (key) {
                case "description":
                    product.setDescription(value);
                    break;
                case "price":
                    product.setPrice(Double.parseDouble(value));
                    break;
                case "quantity":
                    product.setQuantityInStock(Integer.parseInt(value));
                    break;
                case "isWholesale":
                    product.setWholesale(Boolean.parseBoolean(value));
                    break;
                default:
                    break;
            }
        }

        return product;
    }

    public static DiscountCard parseDiscountCardFromRequest(HttpServletRequest req) throws IOException {
        BufferedReader reader = req.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        String json = jsonBuilder.toString();
        DiscountCard discountCard = new DiscountCard(0, 0, (short) 0);

        json = json.replace("{", "").replace("}", "").replace("\"", "");
        String[] fields = json.split(",");
        for (String field : fields) {
            String[] keyValue = field.split(":");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            switch (key) {
                case "discountCard":
                    discountCard.setNumber(Integer.parseInt(value));
                    break;
                case "discountAmount":
                    discountCard.setAmount(Short.parseShort(value));
                    break;
                default:
                    break;
            }
        }

        return discountCard;
    }
}
