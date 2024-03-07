package com.mandacarubroker.exception;

public class StockTransactionFoundException extends RuntimeException {
    public StockTransactionFoundException() {
        super("Transação já existe, caso queira modificá-la, atualize");
    }
}