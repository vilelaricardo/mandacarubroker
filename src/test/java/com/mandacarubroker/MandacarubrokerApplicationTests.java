package com.mandacarubroker;

import com.mandacarubroker.controller.StockController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MandacarubrokerApplicationTests {
	@Autowired
	private StockController controller;
	@Test
	void contextLoads() {
		Assertions.assertNotNull(controller);
	}

}
