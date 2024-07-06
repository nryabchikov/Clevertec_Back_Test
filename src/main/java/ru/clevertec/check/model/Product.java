package main.java.ru.clevertec.check.model;

public class Product {
    private int id;
    private final String description;
    private final double price;
    private final int quantityInStock;
    private final boolean isWholesale;


    public Product(int id, String description, double price, int quantityInStock, boolean isWholesale) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.isWholesale = isWholesale;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public String getDescription() {
        return description;
    }
    public double getPrice() {
        return price;
    }
    public boolean isWholesale() {
        return isWholesale;
    }
}
