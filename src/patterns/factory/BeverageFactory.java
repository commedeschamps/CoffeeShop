package patterns.factory;

import model.beverage.*;
import model.menu.MenuItem;

public class BeverageFactory implements MenuItemFactory {

    @Override
    public MenuItem createItem(String code) {
        return switch (code) {
            case "ESP"      -> new Espresso();
            case "LAT"      -> new Latte();
            case "ICED_LAT" -> new IcedLatte();
            case "B_TEA"    -> new BlackTea();
            case "G_TEA"    -> new GreenTea();
            case "H_TEA"    -> new HerbalTea();
            case "LEM"      -> new Lemonade();
            default -> throw new IllegalArgumentException("Unknown beverage code: " + code);
        };
    }
}
