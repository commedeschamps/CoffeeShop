package patterns.factory;

import model.beverage.*;
import model.menu.MenuItem;
import patterns.decorator.ToppingCompatible;
import patterns.decorator.ToppingType;

public class BeverageMenuFactory implements MenuItemFactory {

    @Override
    public MenuItem createItem(String code) {
        Beverage beverage = switch (code) {
            case "ESP" -> new Espresso();
            case "LAT" -> new Latte();
            case "ICED_LAT" -> new IcedLatte();
            case "AME" -> new Americano();
            case "CAP" -> new Cappuccino();
            case "HOT_CHOC" -> new HotChocolate();
            case "B_TEA" -> new BlackTea();
            case "G_TEA" -> new GreenTea();
            case "H_TEA" -> new HerbalTea();
            case "LEM" -> new Lemonade();
            default -> throw new IllegalArgumentException("Unknown beverage code: " + code);
        };
        return new DrinkMenuItem(beverage);
    }

    // Adapter that exposes Beverage as MenuItem and preserves ToppingCompatible
    private static class DrinkMenuItem implements MenuItem, Beverage, ToppingCompatible {
        private final Beverage delegate;
        DrinkMenuItem(Beverage delegate) { this.delegate = delegate; }
        @Override public String getDescription() { return delegate.getDescription(); }
        @Override public double getBaseCost() { return delegate.getBaseCost(); }
        @Override public boolean supports(ToppingType topping) {
            if (delegate instanceof ToppingCompatible compat) {
                return compat.supports(topping);
            }
            return false;
        }
    }
}

