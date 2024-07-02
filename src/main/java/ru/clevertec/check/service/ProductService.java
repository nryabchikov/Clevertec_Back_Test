package main.java.ru.clevertec.check.service;

import main.java.ru.clevertec.check.model.Product;
import main.java.ru.clevertec.check.repository.ProductRepository;

import java.io.IOException;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(String productFilePath) throws IOException {
        this.productRepository = new ProductRepository(productFilePath);
    }

    public Product getProductById(int id) {
        return productRepository.findProductById(id);
    }

}
