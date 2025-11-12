package model.beverage;

import patterns.decorator.ToppingCompatible;
import patterns.decorator.ToppingType;

public class Cappuccino implements Beverage, ToppingCompatible {

    @Override
    public String getDescription() {
        return "Cappuccino";
    }

    @Override
    public double getBaseCost() {
        return 1100;
    }

    @Override
    public boolean supports(ToppingType topping) {
        return switch (topping) {
            case MILK, SYRUP, EXTRA_SHOT, WHIPPED_CREAM, CINNAMON -> true;
            default -> false;
        };
    }
}
