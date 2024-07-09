package ru.clevertec.check.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.exception.DatabaseCredentialsException;
import ru.clevertec.check.exception.MissingProductsException;
import ru.clevertec.check.exception.ProductLackOfQuantityException;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.service.DiscountCardService;
import ru.clevertec.check.service.ProductService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InputHandlerTest {

    private ProductService productService;
    private DiscountCardService discountCardService;
    private String[] args = {
            "datasource.url=jdbc:postgresql://localhost:5432/clevertec_db",
            "datasource.username=nikitaryabchikov",
            "datasource.password=123",
            "saveToFile=123.csv",
            "balanceDebitCard=45.3",
            "1-5"
    };

    private String[] argsWithoutProducts = {
            "datasource.url=jdbc:postgresql://localhost:5432/clevertec_db",
            "datasource.username=nikitaryabchikov",
            "datasource.password=123",
            "saveToFile=123.csv",
            "balanceDebitCard=45.3"
    };

    @BeforeEach
    public void setUp() throws DatabaseCredentialsException {
        productService = mock(ProductService.class);
        discountCardService = mock(DiscountCardService.class);
    }

    @Test
    public void handleDiscountCard() throws SQLException, IOException {
        String[] argsWithDiscountCard = extendArgs(args, "discountCard=1111");
        when(discountCardService.getAllDiscountCards()).thenReturn(Collections.singletonMap(1111, 3));
        InputHandler inputHandler = new InputHandler(argsWithDiscountCard);
        inputHandler.handleDiscountCard("1111");
        assertEquals(1111, inputHandler.getNumberOfDiscountCard());
        assertEquals(3, inputHandler.getDiscountPercentage());
    }

    @Test
    public void handleBalanceDebitCard() throws SQLException, IOException {
        String[] argsWithBalance = extendArgs(args, "balanceDebitCard=100.0");
        InputHandler inputHandler = new InputHandler(argsWithBalance);
        inputHandler.handleBalanceDebitCard("100.0");
        assertEquals(100.0, inputHandler.getBalanceDebitCard());
    }

    @Test
    public void handleProduct() throws SQLException, IOException {
        Product product = new Product(1, "Milk", 1.07, 10, true);
        when(productService.getProductById(1)).thenReturn(product);
        String[] argsWithProduct = extendArgs(argsWithoutProducts, "1-5");
        InputHandler inputHandler = new InputHandler(argsWithProduct);
        Map<Integer, Integer> mapOfIdAndAmountOfProducts = inputHandler.getMapOfIdAndAmountOfProducts();
        assertEquals(1, mapOfIdAndAmountOfProducts.size());
        assertEquals(5, mapOfIdAndAmountOfProducts.get(1));
    }

    @Test
    public void missingProductsException() throws SQLException {
        when(productService.getProductById(1)).thenReturn(null);
        assertThrows(MissingProductsException.class, () -> {
            new InputHandler(argsWithoutProducts);
        });
    }

    @Test
    public void testProductLackOfQuantityException() throws SQLException {
        Product product = new Product(1, "Milk", 1.07, 10, true);
        String[] argsWithProduct = extendArgs(argsWithoutProducts, "1-15");
        when(productService.getProductById(1)).thenReturn(product);
        assertThrows(ProductLackOfQuantityException.class, () -> {
            new InputHandler(argsWithProduct);
        });
    }

    @Test
    public void testDatabaseCredentialsException() {
        String[] invalidArgs = {};
        assertThrows(DatabaseCredentialsException.class, () -> {
            DatabaseInputHandler.setDatabaseCredentials(invalidArgs);
        });
    }

    private String[] extendArgs(String[] baseArgs, String additionalArg) {
        String[] extendedArgs = new String[baseArgs.length + 1];
        System.arraycopy(baseArgs, 0, extendedArgs, 0, baseArgs.length);
        extendedArgs[baseArgs.length] = additionalArg;
        return extendedArgs;
    }
}
