package patterns.decorator;

import model.beverage.Beverage;

public class WhippedCreamDecorator extends BeverageDecorator {
    public WhippedCreamDecorator(Beverage delegate) { super(delegate); }
    @Override
    public String getDescription() { return delegate.getDescription() + ", whipped cream"; }
    @Override
    public double getBaseCost() { return delegate.getBaseCost() + 180; }
}

