package patterns.strategy;

import model.order.Order;

// Example: 10% discount on total

public class StudentDiscountStrategy implements PricingStrategy {

    @Override
    public double calculateTotal(Order order) {
        double totalWithoutDiscount = new NoDiscountStrategy().calculateTotal(order);
        return totalWithoutDiscount * 0.9;
    }

    @Override
    public String getName() {
        return "Student 10% discount";
    }
}
