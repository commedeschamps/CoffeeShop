package patterns.strategy;

import model.order.Order;

// No discount, just sum up all items

public class NoDiscountStrategy implements PricingStrategy {

    @Override
    public double calculateTotal(Order order) {
        return order.getItems().stream()
                .mapToDouble(item ->
                        item.getItem().getBaseCost() * item.getQuantity()
                )
                .sum();
    }

    @Override
    public String getName() {
        return "No discount";
    }
}
