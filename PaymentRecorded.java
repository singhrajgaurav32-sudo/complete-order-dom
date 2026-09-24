package com.example.orders.domain;

import java.time.Instant;

public record PaymentRecorded(OrderId orderId, Money amount, Instant occurredAt) implements DomainEvent {
}
