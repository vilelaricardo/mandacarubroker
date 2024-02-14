package com.mandacarubroker;

import com.mandacarubroker.domain.stock.Stock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.mandacarubroker.domain.stock.PriceChangeException;

class MandacarubrokerApplicationTests extends Stock {

	@Test
	void changePrice_ValidAmount_ShouldChangePrice() {
		// Arrange
		Stock stock = new Stock();
		double initialPrice = 10.0;
		stock.setPrice(initialPrice);

		// Act
		double newPrice = stock.changePrice(15.0);

		// Assert
		assertEquals(15.0, newPrice);
	}

	@Test
	void changePrice_ZeroAmount_ShouldThrowException() {
		// Arrange
		Stock stock = new Stock();

		// Act & Assert
		assertThrows(PriceChangeException.class, () -> {
			stock.changePrice(0);
		});
	}

	@Test
	void changePrice_NegativeAmount_ShouldThrowException() {
		// Arrange
		Stock stock = new Stock();

		// Act & Assert
		assertThrows(PriceChangeException.class, () -> {
			stock.changePrice(-5.0);
		});
	}
}

