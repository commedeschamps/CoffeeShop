package model.beverage;

import patterns.decorator.types.ToppingCompatible;
import patterns.decorator.types.ToppingType;

public class Lemonade implements Beverage, ToppingCompatible {

    @Override
    public String getDescription() {
        return "Lemonade";
    }

    @Override
    public double getBaseCost() {
        return 900;
    }

    @Override
    public boolean supports(ToppingType topping) {
        // lemonade does not support any toppings(unless for now)
        return false;
    }
}
