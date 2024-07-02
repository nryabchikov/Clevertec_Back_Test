package main.java.ru.clevertec.check.model;

import lombok.Data;

@Data
public class Product {
    private int id;
    private String description;
    private double price;
    private boolean isWholesale;
}
