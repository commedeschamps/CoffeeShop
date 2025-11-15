package model.meal.seasonal;

import model.meal.Meal;

public class PumpkinSoup implements Meal {
    @Override
    public String getDescription() {
        return "Pumpkin Soup";
    }

    @Override
    public double getBaseCost() {
        return 1500;
    }
}
