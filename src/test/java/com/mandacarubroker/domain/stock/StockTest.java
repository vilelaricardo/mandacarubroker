package com.mandacarubroker.domain.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StockTest {

    @DisplayName("Deve ser possível criar um estoque com informações válidas")
    @Test
    void shouldBeAbleToCreateStockWithValidInformation() {
        // Configuração dos parâmetros para criar a instância de Stock
        String companyName = "companyName";
        final double price = 250.0;
        String id = "stock-id";
        String symbol = "symbol";

        // Criação da instância de Stock
        final Stock stock = new Stock(id, symbol, companyName, price);

        // Verificações dos atributos da instância criada
        assertEquals(id, stock.getId());
        assertEquals(symbol, stock.getSymbol());
        assertEquals(companyName, stock.getCompanyName());
        assertEquals(price, stock.getPrice());
    }

    @DisplayName("Não deve ser possível definir o preço do estoque como negativo")
    @Test
    void shouldNotBeAbleToSetNegativeStockPrice() {
        // Criação da instância de Stock com preço negativo
        final Stock stock = new Stock("stock-id", "symbol", "companyName", 75);

        // Verificação se tentar definir um preço negativo resulta em exceção
        assertThrows(IllegalArgumentException.class, () -> {
            stock.setPrice(-75);
        });
    }

    @DisplayName("Não deve ser possível definir o preço do estoque como zero")
    @Test
    void shouldNotBeAbleToSetStockPriceToZero() {
        // Criação da instância de Stock com preço negativo
        final Stock stock = new Stock("stock-id", "symbol", "companyName", -75);

        // Verificação se tentar definir um preço zero resulta em exceção
        assertThrows(IllegalArgumentException.class, () -> {
            stock.setPrice(0);
        });
    }
}
