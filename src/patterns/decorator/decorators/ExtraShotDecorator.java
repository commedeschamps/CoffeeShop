package patterns.decorator.decorators;

import model.beverage.Beverage;

public class ExtraShotDecorator extends BeverageDecorator {

    public ExtraShotDecorator(Beverage delegate) {
        super(delegate);
    }

    @Override
    public String getDescription() {
        return delegate.getDescription() + ", extra shot";
    }

    @Override
    public double getBaseCost() {
        // extra shot a bit cheaper than full espresso
        return delegate.getBaseCost() + 300;
    }
}
