package model.beverage;

import patterns.decorator.ToppingCompatible;
import patterns.decorator.ToppingType;

public class Espresso implements Beverage, ToppingCompatible {

    @Override
    public String getDescription() {
        return "Espresso";
    }

    @Override
    public double getBaseCost() {
        return 800;
    }

    @Override
    public boolean supports(ToppingType topping) {
        return switch (topping) {
            case MILK, SYRUP, EXTRA_SHOT, WHIPPED_CREAM, CINNAMON -> true;
            default -> false;
        };
    }
}
