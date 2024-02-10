package com.mandacarubroker.domain.stock;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StockTest {
    @Test
    void itShouldBeAbleToCreateStock() {
        String id = "stock-id";
        String symbol = "symbol";
        String companyName = "companyName";
        final double price = 100.0;

        final Stock stock = new Stock(id, symbol, companyName, price);

        assertEquals(id, stock.getId());
        assertEquals(symbol, stock.getSymbol());
        assertEquals(companyName, stock.getCompanyName());
        assertEquals(price, stock.getPrice());
    }

    @Test
    void itShouldNotBeAbleToSetNegativeStockPrice() {
        final Stock stock = new Stock("stock-id", "symbol", "companyName", -10);
        assertThrows(IllegalArgumentException.class, () -> {
            stock.setPrice(-10);
        });
    }

    @Test
    void itShouldNotBeAbleToSetStockPriceToZero() {
        final Stock stock = new Stock("stock-id", "symbol", "companyName", -10);
        assertThrows(IllegalArgumentException.class, () -> {
            stock.setPrice(0);
        });
    }


    @Test
    void itShouldIncreasePriceOnStockPriceIncrease() {
        final Stock stock = new Stock("stock-id", "symbol", "companyName", 100.0);
        final double amount = 10.0;
        final boolean increase = true;

        final double result = stock.changePrice(amount, increase);

        assertEquals(110, result);
    }

    @Test
    void itShouldDecreasePriceOnStockPriceDecrease() {
        final Stock stock = new Stock("stock-id", "symbol", "companyName", 100.0);
        final double amount = 10.0;
        final boolean increase = false;

        final double result = stock.changePrice(amount, increase);

        assertEquals(90, result);
    }

    @Test
    void itShouldNotBeAbleToReceiveNegativeAmountForPriceChange() {
        final Stock stock = new Stock("stock-id", "symbol", "companyName", 100.0);
        final double amount = -10.0;
        final boolean increase = false;

        assertThrows(IllegalArgumentException.class, () -> {
            stock.changePrice(amount, increase);
        });
    }
}
