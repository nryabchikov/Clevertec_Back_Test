package ru.clevertec.check.util;

import ru.clevertec.check.model.Check;
import ru.clevertec.check.model.Item;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CsvWriter {
    public static void writeCheckToCsv(Check check, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            writer.append("Date;Time\n");
            writer.append(dateFormatter.format(now)).append(";");
            writer.append(timeFormatter.format(now)).append("\n");

            writer.append("\nQTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL").append("\n");
            for (Item item: check.getItems()) {
                writer.append(String.valueOf(item.getAmount())).append(";");
                writer.append(item.getProduct().getDescription()).append(";");
                writer.append(String.format("%.2f", MathRounder.roundNumberToTwoDigits(item.getProduct().getPrice()))
                        .replace(".", ",")).append("$").append(";");
                writer.append(String.format("%.2f", MathRounder.roundNumberToTwoDigits(item.getDiscount())))
                        .append("$").append(";");
                writer.append(String.format("%.2f", MathRounder.roundNumberToTwoDigits(item.getTotal()))
                        .replace(".", ",")).append("$").append("\n");
            }

            if (check.getNumberOfDiscountCard() != 0) {
                writer.append("\nDISCOUNT CARD;DISCOUNT PERCENTAGE").append("\n");
                writer.append(String.valueOf(check.getNumberOfDiscountCard())).append(";");
                writer.append(String.valueOf(check.getDiscountPercentage())).append("%").append("\n");
            }

            writer.append("\nTOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT").append("\n");
            writer.append(String.format("%.2f", MathRounder.roundNumberToTwoDigits(check.getTotalPrice())))
                    .append("$").append(";");
            writer.append(String.format("%.2f", MathRounder.roundNumberToTwoDigits(check.getTotalDiscount())))
                    .append("$").append(";");
            writer.append(String.format("%.2f", MathRounder.roundNumberToTwoDigits(check.getTotalPriceWithDiscount())))
                    .append("$");
        }
    }
    public static void writeErrorToCsv(Status status, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("ERROR\n");
            writer.append(status.getMessage());
        }
    }
}

