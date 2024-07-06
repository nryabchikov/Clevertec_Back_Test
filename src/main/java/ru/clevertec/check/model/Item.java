package main.java.ru.clevertec.check.model;

public class Item {
    private final Product product;
    private final int amount;
    private final double total;
    private final double discount;

    public double getDiscount() {
        return discount;
    }
    public Item(Product product, int amount, double discount) {
        this.product = product;
        this.amount = amount;
        this.discount = discount;
        this.total = product.getPrice() * amount;
    }

    public Product getProduct() {
        return product;
    }
    public int getAmount() {
        return amount;
    }
    public double getTotal() {
        return total;
    }
}
