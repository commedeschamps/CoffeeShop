package patterns.strategy;

import model.order.Order;

public interface PricingStrategy {
    double calculateTotal(Order order);
    String getName();
}
