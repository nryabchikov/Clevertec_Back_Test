package ru.clevertec.check.model;

public class Product {
    private int id;
    private String description;
    private double price;
    private int quantityInStock;
    private boolean isWholesale;


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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public void setWholesale(boolean wholesale) {
        isWholesale = wholesale;
    }
}
