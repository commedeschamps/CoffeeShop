package patterns.factory;

import model.beverage.*;
import model.dessert.*;
import model.meal.*;

public class StandardMenuFactory implements MenuFactory {
    @Override
    public Beverage createBeverage(String code) {
        String c = code.toUpperCase();
        return switch (c) {
            // Espresso family
            case "ESP", "ESPRESSO" -> new Espresso();
            case "LAT", "LATTE" -> new Latte();
            case "ICED_LAT", "ICED LATTE", "ICED_LATTE" -> new IcedLatte();
            case "AME", "AMERICANO" -> new Americano();
            case "CAP", "CAPPUCCINO" -> new Cappuccino();
            case "HOT_CHOC", "HOT CHOCOLATE", "HOTCHOCOLATE" -> new HotChocolate();
            // Teas and lemonade
            case "B_TEA", "BLACK TEA", "BLACK_TEA" -> new BlackTea();
            case "G_TEA", "GREEN TEA", "GREEN_TEA" -> new GreenTea();
            case "H_TEA", "HERBAL TEA", "HERBAL_TEA" -> new HerbalTea();
            case "LEM", "LEMONADE" -> new Lemonade();
            default -> throw new IllegalArgumentException("Unknown beverage code: " + code);
        };
    }

    @Override
    public Dessert createDessert(String code){
        String c = code.toUpperCase();
        return switch (c){
            case "CHEESE", "CHEESECAKE" -> new Cheesecake();
            case "BROWNIE" -> new Brownie();
            case "MUFFIN" -> new Muffin();
            case "CROISSANT" -> new Croissant();
            default -> throw new IllegalArgumentException("Unknown dessert code: " + code);
        };
    }

    @Override
    public Meal createMeal(String code){
        String c = code.toUpperCase();
        return switch (c){
            case "SANDWICH" -> new Sandwich();
            case "SALAD" -> new Salad();
            case "LUNCHBOX" -> new Lunchbox();
            default -> throw new IllegalArgumentException("Unknown meal code: " + code);
        };
    }
}
