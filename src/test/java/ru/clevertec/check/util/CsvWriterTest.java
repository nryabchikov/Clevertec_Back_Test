package ru.clevertec.check.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.model.Check;
import ru.clevertec.check.model.Item;
import ru.clevertec.check.model.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CsvWriterTest {

    private Check check;

    @BeforeEach
    public void setUp() {
        Product product1 = new Product(1, "Milk", 1.07, 10, true);
        Product product2 = new Product(2, "Bread", 0.8, 20, false);
        Item item1 = new Item(product1, 2, 3);
        Item item2 = new Item(product2, 3, 5);

        check = new Check();
        check.setItems(Arrays.asList(item1, item2));
        check.setNumberOfDiscountCard(1111);
        check.setDiscountPercentage(5);
        check.setTotalPrice(72.00);
        check.setTotalDiscount(3.00);
        check.setTotalPriceWithDiscount(69.00);
    }

    @Test
    public void writeCheckToCsv() throws IOException {
        Path tempFile = Files.createTempFile("result", ".csv");
        tempFile.toFile().deleteOnExit();
        CsvWriter.writeCheckToCsv(check, tempFile.toString());
        String content = Files.readString(tempFile);
        assertThat(content).contains("Date;Time");
        assertThat(content).contains("QTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL");
        assertThat(content).contains("2;Milk;1,07$;3,00$");
        assertThat(content).contains("3;Bread;0,80$;5,00$");
        assertThat(content).contains("DISCOUNT CARD;DISCOUNT PERCENTAGE");
        assertThat(content).contains("1111;5%");
        assertThat(content).contains("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT");
        assertThat(content).contains("72,00$;3,00$;69,00$");
    }

    @Test
    public void writeErrorToCsv() throws IOException {
        Path tempFile = Files.createTempFile("result", ".csv");
        tempFile.toFile().deleteOnExit();
        Status status = mock(Status.class);

        when(status.getMessage()).thenReturn("Test error message");
        CsvWriter.writeErrorToCsv(status, tempFile.toString());
        String content = Files.readString(tempFile);
        assertThat(content).contains("ERROR");
        assertThat(content).contains("Test error message");
    }
    @Test
    public void writeErrorToCsvThrowsIOException() {
        String filePath = "/invalid/path/test-error.csv";
        Status status = mock(Status.class);

        when(status.getMessage()).thenReturn("Test error message");
        assertThrows(IOException.class, () -> {
            CsvWriter.writeErrorToCsv(status, filePath);
        });
    }
}
