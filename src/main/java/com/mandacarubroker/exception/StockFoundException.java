package com.mandacarubroker.exception;

public class StockFoundException extends RuntimeException {
    public StockFoundException() {
        super("Ação já existe");
    }
}