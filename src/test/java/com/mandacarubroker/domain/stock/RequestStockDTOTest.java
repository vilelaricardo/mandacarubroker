package com.mandacarubroker.domain.stock;

import com.mandacarubroker.service.StockService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RequestStockDTOTest {

    @Test
    void symbolPatternValidation() {
        RequestStockDTO request = new RequestStockDTO("AAB2", "Company", 10);

        Assertions.assertThrows(ConstraintViolationException.class,
                () -> StockService.validateRequestStockDTO(request));
    }

    @Test
    void companyNamePatternValidation() {
        RequestStockDTO request = new RequestStockDTO("AB1", "", 12);

        Assertions.assertThrows(ConstraintViolationException.class,
                () -> StockService.validateRequestStockDTO(request));
    }

    @Test
    void pricePatternValidation() {
        RequestStockDTO request = new RequestStockDTO("AA1", "Company AA", 0);

        Assertions.assertThrows(ConstraintViolationException.class,
                () -> StockService.validateRequestStockDTO(request));
    }

    @Test
    void requestDataPattern() {
        RequestStockDTO request = new RequestStockDTO("AB1", "My company", 120.32);

        Assertions.assertDoesNotThrow(() -> StockService.validateRequestStockDTO(request));
    }

}
