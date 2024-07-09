package ru.clevertec.check.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.check.model.Check;
import ru.clevertec.check.repository.CheckRepository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CheckServiceTest {

    private CheckService checkService;
    private CheckRepository checkRepository;

    @BeforeEach
    public void setUp() {
        checkRepository = mock(CheckRepository.class);
        checkService = new CheckService(checkRepository);
    }

    @Test
    public void generateCheck() throws SQLException {
        Map<Integer, Integer> mapOfIdAndAmountOfProducts = new HashMap<>();
        mapOfIdAndAmountOfProducts.put(1, 2);
        mapOfIdAndAmountOfProducts.put(2, 3);
        int numberOfDiscountCard = 1111;
        int discountPercentage = 3;
        double balanceDebitCard = 100.0;

        Check expectedCheck = new Check();
        when(checkRepository.generateCheck(mapOfIdAndAmountOfProducts, numberOfDiscountCard,
                discountPercentage, balanceDebitCard)).thenReturn(expectedCheck);
        Check actualCheck = checkService.generateCheck(mapOfIdAndAmountOfProducts, numberOfDiscountCard,
                discountPercentage, balanceDebitCard);
        assertEquals(expectedCheck, actualCheck);
        verify(checkRepository, times(1)).generateCheck(mapOfIdAndAmountOfProducts, numberOfDiscountCard,
                discountPercentage, balanceDebitCard);
    }

    @Test
    public void generateCheckThrowsSQLException() throws SQLException {
        Map<Integer, Integer> mapOfIdAndAmountOfProducts = new HashMap<>();
        mapOfIdAndAmountOfProducts.put(1, 2);
        mapOfIdAndAmountOfProducts.put(2, 3);
        int numberOfDiscountCard = 12345;
        int discountPercentage = 10;
        double balanceDebitCard = 100.0;

        when(checkRepository.generateCheck(mapOfIdAndAmountOfProducts, numberOfDiscountCard,
                discountPercentage, balanceDebitCard)).thenThrow(new SQLException("Invalid credentials."));
        assertThrows(SQLException.class, () -> {
            checkService.generateCheck(mapOfIdAndAmountOfProducts, numberOfDiscountCard,
                    discountPercentage, balanceDebitCard);
        });
        verify(checkRepository, times(1)).generateCheck(mapOfIdAndAmountOfProducts, numberOfDiscountCard,
                discountPercentage, balanceDebitCard);
    }
}
