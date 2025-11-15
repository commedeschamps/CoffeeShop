package patterns.decorator.decorators;

import model.beverage.Beverage;
import patterns.decorator.types.MilkType;

public class MilkDecorator extends BeverageDecorator {

    private final MilkType milkType;

    public MilkDecorator(Beverage delegate, MilkType milkType) {
        super(delegate);
        this.milkType = milkType;
    }


    @Override
    public String getDescription() {
        if (milkType == null) {
            return delegate.getDescription() + ", milk";
        }
        return delegate.getDescription() + ", " + toHumanReadable(milkType) + " milk";
    }
    public String toHumanReadable(MilkType milkType) {
        return switch (milkType) {
            case ALMOND -> "almond";
            case COCONUT -> "coconut";
            case OAT -> "oat";
            case WHOLE -> "whole";
        };
    }

    @Override
    public double getBaseCost() {
        double extra = 150;
        if (milkType != null) {
            //alternative milks are slightly expensive
            extra = 200;
        }
        return delegate.getBaseCost() + extra;
    }

    public MilkType getMilkType() {
        return milkType;
    }

}
