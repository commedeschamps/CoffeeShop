package patterns.strategy;


import model.order.Order;

// Example: 20% discount on all drinks

public class HappyHourStrategy implements PricingStrategy {

    @Override
    public double calculateTotal(Order order) {
        double total = order.getItems().stream()
                .mapToDouble(item -> item.getItem().getBaseCost() * item.getQuantity())
                .sum();
        return total * 0.8; // 20% off
    }

    @Override
    public String getName() {
        return "Happy Hour 20% discount";
    }
}

