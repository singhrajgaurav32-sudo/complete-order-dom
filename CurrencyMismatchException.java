package com.example.orders.domain;

import java.util.Currency;

public class CurrencyMismatchException extends DomainException {
    public CurrencyMismatchException(Currency expected, Currency actual) {
        super("Currency mismatch: expected " + expected + " but got " + actual);
    }
}
