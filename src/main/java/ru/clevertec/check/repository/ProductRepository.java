package main.java.ru.clevertec.check.repository;

import main.java.ru.clevertec.check.exception.ProductNotFoundException;
import main.java.ru.clevertec.check.model.Product;
import main.java.ru.clevertec.check.util.CsvReader;
import main.java.ru.clevertec.check.util.CsvWriter;
import main.java.ru.clevertec.check.util.Status;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProductRepository {
    private final Map<Integer, Product> products = new HashMap<>();

    public ProductRepository(String filePath) throws IOException {
        loadProductsFromFile(filePath);
    }

    private void loadProductsFromFile(String filePath) throws IOException {
        List<String[]> mapOfStringProduct = CsvReader.readProducts(filePath);
        for (String[] stringProduct: mapOfStringProduct) {
            int id = Integer.parseInt(stringProduct[0]);
            String description = stringProduct[1];
            double price = Double.parseDouble(stringProduct[2].replace(",", "."));
            int quantityInStock = Integer.parseInt(stringProduct[3]);
            boolean isWholesale = Objects.equals(stringProduct[4], "+");
            products.put(id, new Product(id, description, price, quantityInStock, isWholesale));
        }
    }

    public Product findProductById(int id) throws IOException {
        if (!(products.containsKey(id))) {
            CsvWriter.writeErrorToCsv(Status.BAD_REQUEST, "result.csv");
            throw new ProductNotFoundException("Product with id: " + id + " not found.");
        }
        return products.get(id);
    }
}
