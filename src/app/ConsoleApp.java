package app;

import patterns.decorator.types.ToppingCompatible;
import patterns.decorator.types.ToppingType;
import patterns.decorator.types.MilkType;
import patterns.decorator.types.SyrupType;
import patterns.factory.*;
import model.menu.MenuItem;
import patterns.strategy.*;
import model.order.Order;
import patterns.facade.CoffeeShopFacade;
import patterns.facade.DrinkRequest;
import patterns.adapter.adapters.KaspiPaymentAdapter;
import patterns.adapter.adapters.HalykPaymentAdapter;
import patterns.adapter.external.KaspiPaymentAPI;
import patterns.adapter.external.HalykPaymentAPI;
import patterns.adapter.standart.CashPayment;
import patterns.adapter.standart.PaymentProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {

    public static void main(String[] args) {
        //choose menu factory variant (Standard or Autumn)
        MenuFactory menuFactory = chooseMenuFactory();

        PricingStrategy pricingStrategy = new NoDiscountStrategy();
        PaymentProcessor paymentProcessor = choosePaymentProcessor();
        CoffeeShopFacade facade = new CoffeeShopFacade(menuFactory, pricingStrategy, paymentProcessor);

        Scanner scanner = new Scanner(System.in);
        List<Order> orderHistory = new ArrayList<>();

        System.out.println("=== Welcome to Coffee Shop ===");

        boolean running = true;
        while (running) {
            System.out.println("\nMain menu:");
            System.out.println("1) Create new order");
            System.out.println("2) Change pricing strategy");
            System.out.println("3) Show order history count");
            System.out.println("0) Exit");
            System.out.print("Your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    Order order = handleNewOrder(scanner, facade, menuFactory instanceof AutumnMenuFactory);
                    if (order != null) orderHistory.add(order);
                }
                case "2" -> chooseStrategy(scanner, facade);
                case "3" -> System.out.println("You have created " + orderHistory.size() + " orders.");
                case "0" -> running = false;
                default -> System.out.println("Unknown option.");
            }
        }

        System.out.println("Goodbye!");
    }

    private static MenuFactory chooseMenuFactory() {
        System.out.println("Choose menu variant:");
        System.out.println("1) Standard menu");
        System.out.println("2) Autumn seasonal menu");
        System.out.print("Your choice (default: 1): ");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().trim();

        if ("2".equals(choice)) {
            System.out.println("🍂 Autumn menu selected! Seasonal items available.");
            return new AutumnMenuFactory();
        } else {
            System.out.println("Standard menu selected.");
            return new StandardMenuFactory();
        }
    }

    private static Order handleNewOrder(Scanner scanner, CoffeeShopFacade facade, boolean isAutumnMenu) {
        facade.startNewOrder();
        boolean addedAny = false;

        while (true) {
            System.out.println("\nAdd item:");
            System.out.println("Drinks:");
            System.out.println(" 1) Espresso");
            System.out.println(" 2) Latte");
            System.out.println(" 3) Iced latte");
            System.out.println(" 4) Black tea");
            System.out.println(" 5) Green tea");
            System.out.println(" 6) Herbal tea");
            System.out.println(" 7) Lemonade");
            System.out.println(" 8) Cappuccino");
            System.out.println(" 9) Americano");
            System.out.println("10) Hot Chocolate");

            if (isAutumnMenu) {
                System.out.println("🍂 Autumn Specials:");
                System.out.println("21) Pumpkin Macchiato");
                System.out.println("22) Ginger Tea");
            }

            System.out.println("Desserts:");
            System.out.println("11) Cheesecake");
            System.out.println("12) Brownie");
            System.out.println("13) Muffin ");
            System.out.println("14) Croissant");

            if (isAutumnMenu) {
                System.out.println("🍂 Autumn Specials:");
                System.out.println("23) Caramel Apple Pie");
                System.out.println("24) Cinnamon Roll");
            }

            System.out.println("Meals:");
            System.out.println("15) Lunchbox");
            System.out.println("16) Salad");
            System.out.println("17) Sandwich ");

            if (isAutumnMenu) {
                System.out.println("🍂 Autumn Specials:");
                System.out.println("25) Pumpkin Soup");
            }

            System.out.println("0) Finish adding items");
            System.out.print("Your choice: ");

            String itemChoice = scanner.nextLine().trim();
            if ("0".equals(itemChoice)) break;

            String code = switch (itemChoice) {
                case "1" -> "ESP";
                case "2" -> "LAT";
                case "3" -> "ICED_LAT";
                case "4" -> "B_TEA";
                case "5" -> "G_TEA";
                case "6" -> "H_TEA";
                case "7" -> "LEM";
                case "8" -> "CAP";
                case "9" -> "AME";
                case "10" -> "HOT_CHOC";
                case "11" -> "CHEESE";
                case "12" -> "BROWNIE";
                case "13" -> "MUFFIN";
                case "14" -> "CROISSANT";
                case "15" -> "LUNCHBOX";
                case "16" -> "SALAD";
                case "17" -> "SANDWICH";
                // Autumn items
                case "21" -> "PUMPKIN_MACCHIATO";
                case "22" -> "GINGER_TEA";
                case "23" -> "CARAMEL_APPLE_PIE";
                case "24" -> "CINNAMON_ROLL";
                case "25" -> "PUMPKIN_SOUP";
                default -> {
                    System.out.println("Unknown code, defaulting to ESP");
                    yield "ESP";
                }
            };

            System.out.print("Quantity: ");
            int qty;
            try {
                qty = Integer.parseInt(scanner.nextLine().trim());
                if (qty <= 0) qty = 1;
            } catch (NumberFormatException e) {
                qty = 1;
            }

            MenuItem baseItem = facade.createBaseItem(code);

            if (baseItem instanceof model.beverage.Beverage base) {
                boolean withMilk = false, withSyrup = false, withExtraShot = false, withWhippedCream = false, withCinnamon = false;
                MilkType milkType = null;
                SyrupType syrupType = null;

                if (supports(base, ToppingType.MILK)) {
                    System.out.print("Add milk? (y/n): ");
                    withMilk = scanner.nextLine().trim().equalsIgnoreCase("y");
                    if (withMilk) {
                        milkType = promptMilkType(scanner);
                    }
                }

                if (supports(base, ToppingType.SYRUP)) {
                    System.out.print("Add syrup? (y/n): ");
                    withSyrup = scanner.nextLine().trim().equalsIgnoreCase("y");
                    if (withSyrup) {
                        syrupType = promptSyrupType(scanner);
                    }
                }

                if (supports(base, ToppingType.EXTRA_SHOT)) {
                    System.out.print("Add extra shot? (y/n): ");
                    withExtraShot = scanner.nextLine().trim().equalsIgnoreCase("y");
                }

                if (supports(base, ToppingType.WHIPPED_CREAM)) {
                    System.out.print("Add whipped cream? (y/n): ");
                    withWhippedCream = scanner.nextLine().trim().equalsIgnoreCase("y");
                }

                if (supports(base, ToppingType.CINNAMON)) {
                    System.out.print("Add cinnamon? (y/n): ");
                    withCinnamon = scanner.nextLine().trim().equalsIgnoreCase("y");
                }

                facade.addCustomizedDrink(new DrinkRequest(
                        code,
                        withMilk,
                        withSyrup,
                        syrupType,
                        milkType,
                        withExtraShot,
                        qty,
                        withWhippedCream,
                        withCinnamon
                ));
            } else {
                facade.addSimpleItem(code, qty);
            }

            addedAny = true;
            System.out.println();
            facade.printCurrentDraft();
        }

        if (!addedAny) {
            System.out.println("No items added. Order canceled.");
            return null;
        }

        System.out.println("\nProceed to checkout...");
        facade.printCurrentDraft();
        if (!confirm(scanner, "Proceed to checkout and pay? (y/n): ")) {
            System.out.println("Order canceled.");
            return null;
        }

        Order order = facade.checkoutAndPay();

        System.out.println("\n=== Final order summary ===");
        order.getItems().forEach(item -> {
            var menuItem = item.getItem();
            System.out.println("- " + menuItem.getDescription()
                    + " x" + item.getQuantity()
                    + " = " + (menuItem.getBaseCost() * item.getQuantity()) + " ₸");
        });
        System.out.println("Final status: " + order.getStatus());
        return order;
    }

    private static void chooseStrategy(Scanner scanner, CoffeeShopFacade facade) {
        System.out.println("\nChoose pricing strategy:");
        System.out.println("1) No discount");
        System.out.println("2) Student 10% discount");
        System.out.println("3) Happy Hour 20% discount");
        System.out.print("Your choice: ");
        String choice = scanner.nextLine().trim();

        PricingStrategy strategy = switch (choice) {
            case "2" -> new StudentDiscountStrategy();
            case "3" -> new HappyHourStrategy();
            default -> new NoDiscountStrategy();
        };
        facade.setPricingStrategy(strategy);
    }

    private static boolean confirm(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String ans = scanner.nextLine().trim();
        return ans.equalsIgnoreCase("y") || ans.equalsIgnoreCase("yes");
    }

    private static boolean supports(model.beverage.Beverage beverage, ToppingType type) {
        if (beverage instanceof ToppingCompatible compat) {
            return compat.supports(type);
        }
        return false;
    }

    private static MilkType promptMilkType(Scanner scanner) {
        System.out.println("Choose milk type:");
        System.out.println("1) Whole");
        System.out.println("2) Oat");
        System.out.println("3) Almond");
        System.out.println("4) Coconut");
        System.out.print("Your choice: ");
        String ch = scanner.nextLine().trim();
        return switch (ch) {
            case "2" -> MilkType.OAT;
            case "3" -> MilkType.ALMOND;
            case "4" -> MilkType.COCONUT;
            default -> MilkType.WHOLE;
        };
    }

    private static SyrupType promptSyrupType(Scanner scanner) {
        System.out.println("Choose syrup type:");
        System.out.println("1) Vanilla");
        System.out.println("2) Caramel");
        System.out.println("3) Chocolate");
        System.out.println("4) Hazelnut");
        System.out.println("5) Salted caramel");
        System.out.print("Your choice: ");
        String ch = scanner.nextLine().trim();
        return switch (ch) {
            case "2" -> SyrupType.CARAMEL;
            case "3" -> SyrupType.CHOCOLATE;
            case "4" -> SyrupType.HAZELNUT;
            case "5" -> SyrupType.SALT_CARAMEL;
            default -> SyrupType.VANILLA;
        };
    }

    private static PaymentProcessor choosePaymentProcessor() {
        System.out.println("Choose payment method:");
        System.out.println("1) Cash");
        System.out.println("2) Kaspi QR");
        System.out.println("3) Halyk Bank");
        System.out.print("Your choice (default: 1): ");
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine().trim();
        return switch (choice) {
            case "2" -> new KaspiPaymentAdapter(new KaspiPaymentAPI());
            case "3" -> new HalykPaymentAdapter(new HalykPaymentAPI());
            default -> new CashPayment();
        };
    }
}
