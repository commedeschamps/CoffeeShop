package model.beverage;

import patterns.decorator.types.ToppingCompatible;
import patterns.decorator.types.ToppingType;

public class BlackTea implements Beverage, ToppingCompatible {
    @Override
    public String getDescription() {
        return "Black Tea";
    }

    @Override
    public double getBaseCost() {
        return 600;
    }

    @Override
    public boolean supports(ToppingType topping) {
        return switch (topping) {
            case MILK -> true;
            case SYRUP, EXTRA_SHOT -> false;
            default -> false;
        };
    }
}
