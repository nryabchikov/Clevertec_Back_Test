package ru.clevertec.check.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CsvConverter {
    static void convert(String productFilePath) {
        String outputFilePathCsv = getOutputFilePath(productFilePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(productFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePathCsv))) {

            String line;
            boolean isFirstLine = true;
            Map<String, Integer> headerMap = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    String[] headers = line.split(";");
                    for (int i = 0; i < headers.length; i++) {
                        headerMap.put(headers[i], i);
                    }
                    writer.write("id;description;\"price, $\";\"quantity in stock\";\"wholesale product\"\n");
                    isFirstLine = false;
                } else {
                    String[] parts = line.split(";");
                    String id = parts[headerMap.get("id")];
                    String description = parts[headerMap.get("description")];
                    String price = parts[headerMap.get("price")].replace(".", ",");
                    String quantityInStock = parts[headerMap.get("quantity_in_stock")];
                    String wholesaleProduct = parts[headerMap.get("wholesale_product")].equals("true") ? "+" : "-";
                    writer.write(id + ";" + description + ";" + price + ";" + quantityInStock + ";" + wholesaleProduct + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getOutputFilePath(String productFilePath) {
        Path path = Paths.get(productFilePath);
        String fileName = path.getFileName().toString();
        String newFileName = "converted_" + fileName;
        return path.getParent().resolve(newFileName).toString();
    }
}
