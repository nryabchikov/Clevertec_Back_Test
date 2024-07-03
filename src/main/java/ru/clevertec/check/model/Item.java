package main.java.ru.clevertec.check.model;

public class Item {
    private Product product;
    private int amount;
    private double total;

    public Item(Product product, int amount, double total) {
        this.product = product;
        this.amount = amount;
        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalPrice() {
        return total;
    }

    public void setTotalPrice(double totalPrice) {
        this.total = totalPrice;
    }
}
