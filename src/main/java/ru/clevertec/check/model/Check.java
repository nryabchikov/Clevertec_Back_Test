package ru.clevertec.check.model;

import java.util.ArrayList;
import java.util.List;

public class Check {
    private List<Item> items;
    private int numberOfDiscountCard;
    private int discountPercentage;
    private double totalPrice;
    private double totalDiscount;

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setNumberOfDiscountCard(int numberOfDiscountCard) {
        this.numberOfDiscountCard = numberOfDiscountCard;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public void setTotalPriceWithDiscount(double totalPriceWithDiscount) {
        this.totalPriceWithDiscount = totalPriceWithDiscount;
    }

    private double totalPriceWithDiscount;

    private Check(CheckBuilder builder) {
        this.items = builder.items;
        this.numberOfDiscountCard = builder.numberOfDiscountCard;
        this.discountPercentage = builder.discountPercentage;
        this.totalPrice = builder.totalPrice;
        this.totalDiscount = builder.totalDiscount;
        this.totalPriceWithDiscount = builder.totalPrice - builder.totalDiscount;
    }

    public List<Item> getItems() {
        return items;
    }
    public double getTotalPrice() {
        return totalPrice;
    }

    public int getNumberOfDiscountCard() {
        return numberOfDiscountCard;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public Check() {
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public double getTotalPriceWithDiscount() {
        return totalPriceWithDiscount;
    }

    public static class CheckBuilder {
        private final List<Item> items = new ArrayList<>();
        private int numberOfDiscountCard;
        private int discountPercentage;
        private double totalPrice;
        private double totalDiscount;
        public void setNumberOfDiscountCard(int numberOfDiscountCard) {
            this.numberOfDiscountCard = numberOfDiscountCard;
        }
        public void setDiscountPercentage(int discountPercentage) {
            this.discountPercentage = discountPercentage;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }
        public void setTotalDiscount(double totalDiscount) {
            this.totalDiscount = totalDiscount;
        }

        public void addItem(Item item) {
            items.add(item);
        }

        public Check build() {
            return new Check(this);
        }
    }
}





