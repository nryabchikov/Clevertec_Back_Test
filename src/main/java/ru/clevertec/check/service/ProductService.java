package ru.clevertec.check.service;

import ru.clevertec.check.model.Product;
import ru.clevertec.check.repository.ProductRepository;

import java.io.IOException;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(String productFilePath) throws IOException {
        this.productRepository = new ProductRepository(productFilePath);
    }
    public Product getProductById(int id) throws IOException {
        return productRepository.findProductById(id);
    }
}
