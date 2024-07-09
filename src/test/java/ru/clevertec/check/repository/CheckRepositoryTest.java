package ru.clevertec.check.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.clevertec.check.model.Check;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.service.ProductService;
import ru.clevertec.check.util.MathRounder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CheckRepositoryTest {

    private CheckRepository checkRepository;
    private ProductService productService;

    @BeforeEach
    public void setUp() throws IOException {
        productService = Mockito.mock(ProductService.class);
        checkRepository = new CheckRepository(productService);
    }

    @Test
    public void generateCheck() throws SQLException {
        Product product1 = new Product(1, "Milk", 1.07, 10, true);
        Product product2 = new Product(2, "Bread", 0.8, 20, false);
        when(productService.getProductById(1)).thenReturn(product1);
        when(productService.getProductById(2)).thenReturn(product2);
        Map<Integer, Integer> products = new HashMap<>();
        products.put(1, 5);
        products.put(2, 3);
        Check check = checkRepository.generateCheck(products, 1111, 3, 100.0);
        double totalPrice = MathRounder.roundNumberToTwoDigits(5 * 1.07 + 3 * 0.8);
        double totalDiscount = MathRounder.roundNumberToTwoDigits(5 * 1.07 * 0.1 + 3 * 0.8 * 0.03);
        assertEquals(totalPrice, check.getTotalPrice());
        assertEquals(1111, check.getNumberOfDiscountCard());
        assertEquals(3, check.getDiscountPercentage());
        assertEquals(totalDiscount, MathRounder.roundNumberToTwoDigits(check.getTotalDiscount()));
        assertEquals(totalPrice - totalDiscount, check.getTotalPriceWithDiscount());
    }

    @Test
    public void calculateItemDiscountWholesaleWithAmountLessFive() {
        Product product = new Product(1, "Milk", 1.07, 10, true);
        double discount = checkRepository.calculateItemDiscount(product, 4, 3);
        assertEquals(MathRounder.roundNumberToTwoDigits(1.07 * 4 * 0.03), discount);
    }

    @Test
    public void calculateItemDiscountWholesaleWithAmountGreaterAndEqualFive() {
        Product product = new Product(1, "Milk", 1.07, 10, true);
        double discount = checkRepository.calculateItemDiscount(product, 5, 3);
        assertEquals(MathRounder.roundNumberToTwoDigits(1.07 * 5 * 0.1), discount);
        discount = checkRepository.calculateItemDiscount(product, 7, 3);
        assertEquals(MathRounder.roundNumberToTwoDigits(1.07 * 7 * 0.1), discount);
    }

    @Test
    public void calculateItemDiscountNotWholesale() {
        Product product = new Product(1, "Milk", 1.07, 10, false);
        double discount = checkRepository.calculateItemDiscount(product, 4, 3);
        assertEquals(MathRounder.roundNumberToTwoDigits(1.07 * 4 * 0.03), discount);
        discount = checkRepository.calculateItemDiscount(product, 10, 3);
        assertEquals(MathRounder.roundNumberToTwoDigits(1.07 * 10 * 0.03), discount);
    }
}
