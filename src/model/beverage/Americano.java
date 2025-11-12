package model.beverage;

import patterns.decorator.ToppingCompatible;
import patterns.decorator.ToppingType;

public class Americano implements Beverage, ToppingCompatible {
    @Override
    public String getDescription() { return "Americano"; }
    @Override
    public double getBaseCost() { return 900; }
    @Override
    public boolean supports(ToppingType topping) {
        return switch (topping) {
            case MILK, SYRUP, EXTRA_SHOT, CINNAMON -> true;
            case WHIPPED_CREAM -> false;
            default -> false;
        };
    }
}

