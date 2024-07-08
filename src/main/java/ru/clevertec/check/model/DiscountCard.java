package ru.clevertec.check.model;

public class DiscountCard {
    private int id;
    private int number;
    private short amount;

    public DiscountCard(int id, int number, short amount) {
        this.id = id;
        this.number = number;
        this.amount = amount;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public short getAmount() {
        return amount;
    }

    public void setAmount(short amount) {
        this.amount = amount;
    }
}
