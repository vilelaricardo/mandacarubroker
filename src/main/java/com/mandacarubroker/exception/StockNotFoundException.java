package com.mandacarubroker.exception;

public class StockNotFoundException extends RuntimeException {
    public StockNotFoundException() {
        super("Ação não encontrada");
    }
}