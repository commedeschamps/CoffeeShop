package patterns.facade;

import model.beverage.Beverage;
import model.dessert.Dessert;
import model.meal.Meal;
import model.menu.MenuItem;
import patterns.decorator.decorators.*;
import patterns.decorator.types.ToppingCompatible;
import patterns.decorator.types.ToppingType;
import patterns.factory.MenuFactory;
import patterns.observer.BaristaConsoleObserver;
import patterns.observer.OrderLogObserver;
import patterns.observer.EventListener;
import model.order.Order;
import patterns.builder.OrderBuilder;
import model.order.OrderItem;
import model.order.OrderStatus;
import patterns.strategy.PricingStrategy;
import patterns.adapter.standart.PaymentProcessor;

public class CoffeeShopFacade {

    private final MenuFactory menuFactory;
    private PricingStrategy pricingStrategy;
    private final PaymentProcessor paymentProcessor;

    private OrderBuilder currentBuilder;

    public CoffeeShopFacade(MenuFactory menuFactory,
                            PricingStrategy pricingStrategy,
                            PaymentProcessor paymentProcessor) {
        this.menuFactory = menuFactory;
        this.pricingStrategy = pricingStrategy;
        this.paymentProcessor = paymentProcessor;
    }

    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
        System.out.println(">> Pricing strategy switched to: " + pricingStrategy.getName());
    }

    public void startNewOrder() {
        this.currentBuilder = new OrderBuilder();
        System.out.println(">> New order started.");
    }

    public void addSimpleItem(String code, int qty) {
        // Try dessert
        try {
            Dessert d = menuFactory.createDessert(code);
            currentBuilder.addItem(d, qty);
            System.out.println(">> Added to order: " + d.getDescription() + " x" + qty + " (" + d.getBaseCost() + " ₸ each)");
            return;
        } catch (IllegalArgumentException ignored) {}
        // Try meal
        try {
            Meal m = menuFactory.createMeal(code);
            currentBuilder.addItem(m, qty);
            System.out.println(">> Added to order: " + m.getDescription() + " x" + qty + " (" + m.getBaseCost() + " ₸ each)");
            return;
        } catch (IllegalArgumentException ignored) {}

        System.out.println("!! Unknown menu item: " + code);
    }

    public void addCustomizedDrink(DrinkRequest request) {
        Beverage customized;
        try {
            customized = menuFactory.createBeverage(request.getCode());
        } catch (IllegalArgumentException e) {
            System.out.println("!! Code " + request.getCode() + " is not a valid beverage.");
            return;
        }

        // Apply Toppings
        if (request.isWithMilk() && isToppingSupported(customized, ToppingType.MILK)) {
            customized = new MilkDecorator(customized, request.getMilkType());
        } else if (request.isWithMilk()) {
            System.out.println("!! Milk cannot be added to: " + customized.getDescription());
        }

        if (request.isWithSyrup() && isToppingSupported(customized, ToppingType.SYRUP)) {
            customized = new SyrupDecorator(customized, request.getSyrupType());
        } else if (request.isWithSyrup()) {
            System.out.println("!! Syrup cannot be added to: " + customized.getDescription());
        }

        if (request.isWithExtraShot() && isToppingSupported(customized, ToppingType.EXTRA_SHOT)) {
            customized = new ExtraShotDecorator(customized);
        } else if (request.isWithExtraShot()) {
            System.out.println("!! Extra shot cannot be added to: " + customized.getDescription());
        }

        if (request.isWithWhippedCream() && isToppingSupported(customized, ToppingType.WHIPPED_CREAM)) {
            customized = new WhippedCreamDecorator(customized);
        } else if (request.isWithWhippedCream()) {
            System.out.println("!! Whipped cream cannot be added to: " + customized.getDescription());
        }

        if (request.isWithCinnamon() && isToppingSupported(customized, ToppingType.CINNAMON)) {
            customized = new CinnamonDecorator(customized);
        } else if (request.isWithCinnamon()) {
            System.out.println("!! Cinnamon cannot be added to: " + customized.getDescription());
        }

        currentBuilder.addItem(customized, request.getQuantity());
        System.out.println(">> Added to order: " + customized.getDescription()
                + " x" + request.getQuantity()
                + " (" + customized.getBaseCost() + " ₸ each)");
    }

    private boolean isToppingSupported(Beverage beverage, ToppingType toppingType) {
        return (beverage instanceof ToppingCompatible compat) && compat.supports(toppingType);
    }

    public void printCurrentDraft() {
        if (currentBuilder == null) {
            System.out.println("Order is not started yet.");
            return;
        }

        try {
            Order draft = currentBuilder.build();
            System.out.println("=== Current order draft ===");
            double subtotal = 0.0;
            for (OrderItem item : draft.getItems()) {
                MenuItem menuItem = item.getItem();
                double line = menuItem.getBaseCost() * item.getQuantity();
                subtotal += line;
                System.out.println("- " + menuItem.getDescription()
                        + " x" + item.getQuantity() + " = " + String.format("%.2f", line) + " ₸");
            }
            System.out.println("---------------------------");
            double totalWithStrategy = pricingStrategy.calculateTotal(draft);
            double discount = subtotal - totalWithStrategy;
            if (discount > 0.005) {
                System.out.println("Subtotal (no discounts): " + String.format("%.2f", subtotal) + " ₸");
                System.out.println("Total with " + pricingStrategy.getName() + ": "
                        + String.format("%.2f", totalWithStrategy)
                        + " ₸ (−" + String.format("%.2f", discount) + " ₸)");
            } else {
                System.out.println("Subtotal: " + String.format("%.2f", subtotal) + " ₸");
            }
            System.out.println("===========================");
        } catch (Exception e) {
            System.out.println("Order is still empty.");
        }
    }

    public Order checkoutAndPay() {
        if (currentBuilder == null) {
            throw new IllegalStateException("No order started. Call startNewOrder() first.");
        }

        Order order = currentBuilder.build();

       EventListener barista = new BaristaConsoleObserver();
        EventListener logger = new OrderLogObserver();
        order.getEvents().subscribe(Order.EVENT_STATUS_CHANGED, barista);
        order.getEvents().subscribe(Order.EVENT_STATUS_CHANGED, logger);

        order.setStatus(OrderStatus.IN_PROGRESS);

        double subtotal = order.getItems().stream()
                .mapToDouble(i -> i.getItem().getBaseCost() * i.getQuantity())
                .sum();
        double total = pricingStrategy.calculateTotal(order);
        double discount = subtotal - total;
        if (discount > 0.005) {
            System.out.println("Subtotal (no discounts): " + String.format("%.2f", subtotal) + " ₸");
            System.out.println("Discount applied (" + pricingStrategy.getName() + "): −" + String.format("%.2f", discount) + " ₸");
        }
        System.out.println("Total to pay: " + String.format("%.2f", total) + " ₸");

        boolean success = false;
        try {
            paymentProcessor.processPayment(total);
            success = true;
        } catch (IllegalArgumentException ex) {
            System.out.println("Payment failed: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Unexpected payment error: " + ex.getMessage());
        }

        if (success) {
            order.setStatus(OrderStatus.PAID);
            System.out.println("Your payment of " + String.format("%.2f", total) + " ₸ was successful.");
            System.out.println("\nBarista prepares your order...");
            order.setStatus(OrderStatus.READY);
        } else {
            System.out.println("Order not paid. Status: " + order.getStatus());
        }

        return order;
    }

    public MenuItem createBaseItem(String code) {
        try { return menuFactory.createBeverage(code); } catch (IllegalArgumentException ignored) {}
        try { return menuFactory.createDessert(code); } catch (IllegalArgumentException ignored) {}
        try { return menuFactory.createMeal(code); } catch (IllegalArgumentException ignored) {}
        throw new IllegalArgumentException("Unknown menu item: " + code);
    }
}
