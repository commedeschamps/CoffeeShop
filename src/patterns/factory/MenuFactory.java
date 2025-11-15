package patterns.factory;

import model.beverage.Beverage;
import model.dessert.Dessert;
import model.meal.Meal;

public interface MenuFactory {
    Beverage createBeverage(String code);
    Dessert createDessert(String code);
    Meal createMeal(String code);
}
