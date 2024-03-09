package com.mandacarubroker.exception;

public class StockTransactionNotFoundException extends RuntimeException {
    public StockTransactionNotFoundException() {
        super("Transação não encontrada");
    }
}