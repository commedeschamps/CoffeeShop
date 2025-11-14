package patterns.facade;

import model.beverage.Beverage;
import patterns.decorator.ExtraShotDecorator;
import patterns.decorator.MilkDecorator;
import patterns.decorator.SyrupDecorator;
import patterns.decorator.ToppingCompatible;
import patterns.decorator.ToppingType;
import patterns.decorator.WhippedCreamDecorator;
import patterns.decorator.CinnamonDecorator;

import model.menu.MenuItem;
import patterns.factory.MenuItemFactory;

import patterns.observer.BaristaConsoleObserver;
import patterns.observer.OrderLogObserver;
import patterns.observer.EventListener;

import model.order.Order;
import patterns.builder.OrderBuilder;
import model.order.OrderItem;
import model.order.OrderStatus;

import patterns.strategy.PricingStrategy;

import patterns.adapter.PaymentProcessor;


public class CoffeeShopFacade {

    private final MenuItemFactory menuFactory;
    private PricingStrategy pricingStrategy;
    private final PaymentProcessor paymentProcessor;

    private OrderBuilder currentBuilder;

    public CoffeeShopFacade(MenuItemFactory menuFactory,
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


    public void addSimpleItem(String code, int quantity) {
        MenuItem item = menuFactory.createItem(code);

        currentBuilder.addItem(item, quantity);

        System.out.println(">> Added to order: "
                + item.getDescription()
                + " x" + quantity
                + " (" + item.getBaseCost() + " ₸ each)");
    }


    public void addCustomizedDrink(DrinkRequest request) {
        MenuItem baseItem = menuFactory.createItem(request.getCode());

        if (!(baseItem instanceof Beverage)) {
            System.out.println("!! Code " + request.getCode()
                    + " is not a drink, cannot apply toppings.");
            return;
        }

        Beverage customized = (Beverage) baseItem;

        // MILK
        if (request.isWithMilk()) {
            if (isToppingSupported(customized, ToppingType.MILK)) {
                customized = new MilkDecorator(customized, request.getMilkType());
            } else {
                System.out.println("!! Milk cannot be added to: " + customized.getDescription());
            }
        }

        // SYRUP
        if (request.isWithSyrup()) {
            if (isToppingSupported(customized, ToppingType.SYRUP)) {
                customized = new SyrupDecorator(customized, request.getSyrupType());
            } else {
                System.out.println("!! Syrup cannot be added to: " + customized.getDescription());
            }
        }

        // EXTRA SHOT
        if (request.isWithExtraShot()) {
            if (isToppingSupported(customized, ToppingType.EXTRA_SHOT)) {
                customized = new ExtraShotDecorator(customized);
            } else {
                System.out.println("!! Extra shot cannot be added to: " + customized.getDescription());
            }
        }

        // WHIPPED CREAM
        if (request.isWithWhippedCream()) {
            if (isToppingSupported(customized, ToppingType.WHIPPED_CREAM)) {
                customized = new WhippedCreamDecorator(customized);
            } else {
                System.out.println("!! Whipped cream cannot be added to: " + customized.getDescription());
            }
        }

        // CINNAMON
        if (request.isWithCinnamon()) {
            if (isToppingSupported(customized, ToppingType.CINNAMON)) {
                customized = new CinnamonDecorator(customized);
            } else {
                System.out.println("!! Cinnamon cannot be added to: " + customized.getDescription());
            }
        }

        currentBuilder.addItem(customized, request.getQuantity());

        System.out.println(">> Added to order: "
                + customized.getDescription()
                + " x" + request.getQuantity()
                + " (" + customized.getBaseCost() + " ₸ each)");
    }

    //domain helper: check if beverage supports given topping
    private boolean isToppingSupported(Beverage beverage, ToppingType toppingType) {
        if (beverage instanceof ToppingCompatible compat) {
            return compat.supports(toppingType);
        }
        return false;
    }

    //helper to show current draft order items (without final discount)
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
                var menuItem = item.getItem();
                double line = menuItem.getBaseCost() * item.getQuantity();
                subtotal += line;
                System.out.println("- " + menuItem.getDescription()
                        + " x" + item.getQuantity() + " = " + line + " ₸");
            }
            System.out.println("Subtotal: " + subtotal + " ₸");
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

        // configure event-based subscribers at runtime
        EventListener barista = new BaristaConsoleObserver();
        EventListener logger = new OrderLogObserver();
        order.getEvents().subscribe(Order.EVENT_STATUS_CHANGED, barista);
        order.getEvents().subscribe(Order.EVENT_STATUS_CHANGED, logger);

        order.setStatus(OrderStatus.IN_PROGRESS);

        double total = pricingStrategy.calculateTotal(order);
        System.out.println("Total (" + pricingStrategy.getName() + "): " + total + " ₸");

        boolean success = paymentProcessor.processPayment(total);
        if (success) {
            order.setStatus(OrderStatus.PAID);
            System.out.println("Your payment of " + total + " ₸ was successful.");
            System.out.println("\nBarista prepares your order...");
            order.setStatus(OrderStatus.READY);
        }

        return order;
    }

    public MenuItem createBaseItem(String code) {
        return menuFactory.createItem(code);
    }
}
