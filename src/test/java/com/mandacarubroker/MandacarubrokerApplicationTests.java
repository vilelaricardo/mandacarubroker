package com.mandacarubroker;

import com.mandacarubroker.controller.StockController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MandacarubrokerApplicationTests {
	@Autowired
	private StockController stockController;
	@Test
	void contextLoads() {
		assertNotNull(stockController);
	}

}
