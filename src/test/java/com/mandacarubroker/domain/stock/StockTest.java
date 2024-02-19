package com.mandacarubroker.domain.stock;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StockTest {

    @Test
    void increaseStockPrice() {
        // Arrange
        Stock stock = new Stock(new RequestStockDTO("AB1", "Company", 12.4));
        double expected = 22.4;

        // Act
        stock.setPrice(stock.changePrice(10, true));

        // Assert
        Assertions.assertEquals(expected, stock.getPrice());
    }

    @Test
    void decreaseStockPrice() {
        // Arrange
        Stock stock = new Stock(new RequestStockDTO("AB1", "Company", 7.4));
        double expected = 3.3;
        final double RANGE = 0.0001;

        // Act
        stock.setPrice(stock.changePrice(4.1, false));

        // Assert
        Assertions.assertEquals(expected, stock.getPrice(), RANGE);
    }
}
