package com.mandacarubroker.domain.stock;

public class PriceChangeException extends RuntimeException {
    public PriceChangeException(String message) {
        super(message);
    }
}
