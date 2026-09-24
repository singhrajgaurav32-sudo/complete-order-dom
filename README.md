# Order domain: layer boundaries and architecture decisions

Java 17, Maven multi-module. A non-trivial approval workflow (`DRAFT -> CONFIRMED -> PAID`, with `CANCELLED`)
modelled so that domain rules stay independent of Spring, JPA, HTTP and databases.

## Modules

| Module | Contents | Depends on |
|--------|----------|------------|
| `order-domain` | `Order` aggregate, value objects, events, ports, `OrderService` use cases | nothing (JUnit in test scope) |
| `order-inmemory` | In-memory adapters for the ports + use-case tests | `order-domain` |

## Requirement traceability

| Requirement (from `order-domain-requirements.json`) | Where |
|-----|-----|
| Order contains at least one line before confirmation | `Order.confirm` / `EmptyOrderException`; `OrderTest.emptyOrderCannotBeConfirmed` |
| Quantity is a positive whole number | `Quantity`; `ValueObjectsTest.quantityMustBePositive` |
| Cancelled order cannot be paid | `OrderStatus` table; `OrderTest.cancelledOrderCannotBePaid`, `OrderStatusTest.cancelledOrderCannotBePaid` |
| Paid order cannot return to draft | `OrderStatusTest.paidOrderCannotReturnToDraft`, `nothingCanEverReturnToDraft` |
| Total derived from immutable line prices and quantities | `Order.total()`, `OrderLine`; `OrderTest.totalIsDerivedFromLinePricesAndQuantities` |
| Events `OrderConfirmed`, `PaymentRecorded`, `OrderCancelled` | sealed `DomainEvent`; asserted in `OrderTest` and `OrderServiceTest` |
| Domain independent of Spring, JPA, HTTP, DB | `ArchitectureTest`, ADR 0001 |

## Task checklist

- [x] Entities, value objects, commands, events, invalid transitions extracted (see ADRs 0002, 0004, 0005)
- [x] Domain module in Java 17 with no Spring annotations or database dependencies
- [x] Ports for persistence, notifications and time + in-memory adapters
- [x] ADRs in `docs/adr`
- [x] JUnit 5 tests for legal and illegal transitions

## Build and test

```bash
mvn clean verify
```

## Layout

```
order-architecture/
  pom.xml
  docs/adr/                         ADR 0001-0005
  order-domain/
    src/main/java/com/example/orders/
      domain/                       Order, OrderStatus, Money, Quantity, events, exceptions
      port/                         OrderRepository, NotificationPort, TimeProvider
      application/                  Commands, OrderService
    src/test/java/...               OrderTest, OrderStatusTest, ValueObjectsTest, ArchitectureTest
  order-inmemory/
    src/main/java/...               InMemoryOrderRepository, RecordingNotificationPort, FixedTimeProvider
    src/test/java/...               OrderServiceTest
```
