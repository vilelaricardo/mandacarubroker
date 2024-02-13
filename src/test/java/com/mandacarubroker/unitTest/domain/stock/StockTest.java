package com.mandacarubroker.unitTest.domain.stock;

import com.mandacarubroker.domain.stock.RequestStockDataTransferObject;
import com.mandacarubroker.domain.stock.Stock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {
  RequestStockDataTransferObject requestStock = new RequestStockDataTransferObject("ABCD1", "Company Name", 100.5);
  @Test
  void constructor_ValidRequestStockData_CreatesStockObject() {

    Stock stock = new Stock(requestStock);

    assertNotNull(stock);
    assertEquals(requestStock.symbol(), stock.getSymbol());
    assertEquals(requestStock.companyName(), stock.getCompanyName());
    assertEquals(requestStock.price(), stock.getPrice());
  }

  @Test
  void testChangePrice_WhenIncreasingPrice_ReturnsNewPrice() {
    Stock stock = new Stock(requestStock);
    double initialPrice = stock.getPrice();
    double increaseAmount = 20.0;

    double newPrice = stock.changePrice(increaseAmount, true);

    assertEquals(initialPrice + increaseAmount, newPrice);
  }

  @Test
  void testChangePrice_WhenDecreasingPrice_ReturnsNewPrice() {
    Stock stock = new Stock(requestStock);
    double initialPrice = stock.getPrice();
    double decreaseAmount = 30.0;
    stock.setPrice(initialPrice);

    double newPrice = stock.changePrice(decreaseAmount, false);

    assertEquals(initialPrice - decreaseAmount, newPrice);
  }

  @Test
  void testChangePrice_WhenIncreasingPriceWithNegativeValue_ReturnsSamePrice() {
    Stock stock = new Stock(requestStock);
    double negativeAmount = -10.0;

    assertThrows(IllegalArgumentException.class, () -> {
      stock.changePrice(negativeAmount, true);
    });
  }

  @Test
  void testChangePrice_WhenDecreasingPriceExceedsCurrentPrice_ReturnsZero() {
    Stock stock = new Stock(requestStock);
    double decreaseAmount = 200.0;

    double newPrice = stock.changePrice(decreaseAmount, false);

    assertEquals(0.0, newPrice);
  }
}

