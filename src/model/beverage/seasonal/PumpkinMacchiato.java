package model.beverage.seasonal;

import model.beverage.Beverage;
import patterns.decorator.types.ToppingCompatible;
import patterns.decorator.types.ToppingType;

public class PumpkinMacchiato implements Beverage, ToppingCompatible {
    @Override
    public String getDescription() {
        return "Pumpkin Macchiato";
    }

    @Override
    public double getBaseCost() {
        return 1200;
    }

    @Override
    public boolean supports(ToppingType topping) {
        return switch (topping) {
            case SYRUP, CINNAMON -> true;
            case EXTRA_SHOT, MILK, WHIPPED_CREAM  -> false;
            default -> false;
        };
    }
}
