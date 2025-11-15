package model.beverage.seasonal;

import model.beverage.Beverage;
import patterns.decorator.types.ToppingCompatible;
import patterns.decorator.types.ToppingType;

public class GingerTea implements Beverage, ToppingCompatible {
    @Override
    public String getDescription() {
        return "Ginger Tea";
    }

    @Override
    public double getBaseCost() {
        return 1000;
    }

    @Override
    public boolean supports(ToppingType topping) {
        return switch (topping) {
            case MILK, SYRUP -> true;
            case EXTRA_SHOT -> false;
            case WHIPPED_CREAM -> false;
            case CINNAMON -> true;

            default -> false;
        };
    }

}
