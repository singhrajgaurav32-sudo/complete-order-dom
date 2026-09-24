package com.example.orders.domain;

import java.util.Objects;

public record OrderId(String value) {
    public OrderId {
        Objects.requireNonNull(value, "value");
        if (value.isBlank()) {
            throw new IllegalArgumentException("OrderId must not be blank");
        }
    }

    public static OrderId of(String value) {
        return new OrderId(value);
    }
}
