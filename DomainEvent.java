package com.example.orders.domain;

import java.time.Instant;

public sealed interface DomainEvent permits OrderConfirmed, PaymentRecorded, OrderCancelled {
    OrderId orderId();

    Instant occurredAt();
}
