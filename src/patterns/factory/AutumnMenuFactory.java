package patterns.factory;

import model.beverage.Beverage;
import model.beverage.seasonal.GingerTea;
import model.beverage.seasonal.PumpkinMacchiato;
import model.dessert.Dessert;
import model.dessert.seasonal.CaramelApplePie;
import model.dessert.seasonal.CinnamonRoll;
import model.meal.Meal;
import model.meal.seasonal.PumpkinSoup;


public class AutumnMenuFactory implements MenuFactory{
    private final MenuFactory normalFactory = new StandardMenuFactory();

    @Override
    public Beverage createBeverage(String code) {
        return switch (code.toUpperCase()){
        case "PUMPKIN_MACCHIATO" -> new PumpkinMacchiato();
        case "GINGER_TEA" -> new GingerTea();
        default -> normalFactory.createBeverage(code);
        };
    }

    @Override
    public Dessert createDessert(String code) {
        return switch (code.toUpperCase()){
            case "CARAMEL_APPLE_PIE" -> new CaramelApplePie();
            case "CINNAMON_ROLL" -> new CinnamonRoll();
            default -> normalFactory.createDessert(code);
        };
    }

    @Override
    public Meal createMeal(String code) {
        return switch (code.toUpperCase()){
            case "PUMPKIN_SOUP" -> new PumpkinSoup();
            default -> normalFactory.createMeal(code);
        };
    }
}
