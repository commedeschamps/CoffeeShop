package patterns.decorator;
import model.beverage.Beverage;

public class SyrupDecorator extends BeverageDecorator {

    private final SyrupType syrupType;

    public SyrupDecorator(Beverage delegate, SyrupType syrupType) {
        super(delegate);
        this.syrupType = syrupType;
    }

    @Override
    public String getDescription() {
        return delegate.getDescription() + ", " + toHumanReadable(syrupType) + " syrup";
    }

    @Override
    public double getBaseCost() {
        return delegate.getBaseCost() + 200; // tenge
    }
    private String toHumanReadable(SyrupType type) {
        return switch (type) {
            case VANILLA      -> "vanilla";
            case CARAMEL      -> "caramel";
            case CHOCOLATE    -> "chocolate";
            case HAZELNUT     -> "hazelnut";
            case SALT_CARAMEL -> "salted caramel";
        };
    }
    public SyrupType getSyrupType() {
        return syrupType;
    }
}
