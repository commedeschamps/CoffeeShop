package model.beverage;

import patterns.decorator.ToppingCompatible;
import patterns.decorator.ToppingType;

public class Latte implements Beverage, ToppingCompatible {

    @Override
    public String getDescription() {
        return "Latte";
    }

    @Override
    public double getBaseCost() {
        return 1000; // tenge
    }
    @Override
    public boolean supports(ToppingType topping) {
        return switch (topping) {
            case MILK, SYRUP, EXTRA_SHOT, WHIPPED_CREAM, CINNAMON -> true;
            default -> false;
        };
    }
    }