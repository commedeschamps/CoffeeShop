package model.beverage;

import patterns.decorator.types.ToppingCompatible;
import patterns.decorator.types.ToppingType;

public class GreenTea implements Beverage, ToppingCompatible {

    @Override
    public String getDescription() {
        return "Green tea";
    }

    @Override
    public double getBaseCost() {
        return 650;
    }

    @Override
    public boolean supports(ToppingType topping) {
        return switch (topping) {
            case MILK, SYRUP, EXTRA_SHOT -> false;
            default -> false;
        };
    }
}
