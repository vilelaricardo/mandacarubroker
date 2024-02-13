package com.mandacarubroker.domain;

import com.mandacarubroker.domain.stock.RequestStockDTO;
import com.mandacarubroker.domain.stock.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
class StockTest {
    private Stock stock;

    @BeforeEach
    void setup(){
        stock = new Stock("1","ABC1","Banco do Brasil Sa",50.0);
    }

    @Test
    void constructorShouldReturnStockWhenStockDTOIsAParameter(){
        Stock stockFromDTo = new Stock(new RequestStockDTO("ABC1","Banco do Brasil Sa",50.0));
        assertNotNull(stockFromDTo);
        assertEquals("ABC1", stockFromDTo.getSymbol());
        assertEquals("Banco do Brasil Sa", stockFromDTo.getCompanyName());
        assertEquals(50.0, stockFromDTo.getPrice());
    }

    @Test
    void changePriceShouldReturnIncreasedPriceWhenIncreaseIsTrue(){
        double expectedPrice = 60.0;
        double actual = stock.changePrice(10.0,true);
        assertEquals(expectedPrice,actual);
    }

    @Test
    void changePriceShouldReturnDecreasedPriceWhenIncreaseIsFalse(){
        double expectedPrice = 40.0;
        double actual = stock.changePrice(10.0,false);
        assertEquals(expectedPrice,actual);
    }

    @Test
    void changePriceShouldReturnPriceWhenIncreaseIsFalseAndResultIsNegative(){
        double expectedPrice = stock.getPrice();

        double actual = stock.changePrice(60.0,false);
        assertEquals(expectedPrice,actual);
    }
}
