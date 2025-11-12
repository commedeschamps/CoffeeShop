package model.beverage;

import patterns.decorator.ToppingCompatible;
import patterns.decorator.ToppingType;

public class HotChocolate implements Beverage, ToppingCompatible {
    @Override
    public String getDescription() { return "Hot Chocolate"; }
    @Override
    public double getBaseCost() { return 950; }
    @Override
    public boolean supports(ToppingType topping) {
        return switch (topping) {
            case MILK, SYRUP, WHIPPED_CREAM, CINNAMON -> true;
            case EXTRA_SHOT -> false;
            default -> false;
        };
    }
}

