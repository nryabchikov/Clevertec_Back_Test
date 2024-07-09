package ru.clevertec.check.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.clevertec.check.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DiscountCardRepositoryTest {

    private DiscountCardRepository discountCardRepository;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    void setUp() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/clevertec_db";
        String username = "nikitaryabchikov";
        String password = "123";
        DatabaseConnection.setDatabaseCredentials(url, username, password);
        connection = Mockito.mock(Connection.class);
        preparedStatement = Mockito.mock(PreparedStatement.class);
        resultSet = Mockito.mock(ResultSet.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        discountCardRepository = new DiscountCardRepository() {
            @Override
            protected Connection getConnection() {
                return connection;
            }
        };
    }

    @Test
    void getAllDiscountCards() throws SQLException {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("number")).thenReturn(1234, 5678);
        when(resultSet.getInt("amount")).thenReturn(3, 5);
        Map<Integer, Integer> discountCards = discountCardRepository.getAllDiscountCards();
        assertEquals(2, discountCards.size());
        assertEquals(3, discountCards.get(1234));
        assertEquals(5, discountCards.get(5678));
        verify(connection).prepareStatement("SELECT * FROM discount_card");
        verify(preparedStatement).executeQuery();
        verify(resultSet, times(3)).next();
        verify(resultSet, times(2)).getInt("number");
        verify(resultSet, times(2)).getInt("amount");
    }
}
