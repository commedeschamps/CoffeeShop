package patterns.facade;

import patterns.decorator.MilkType;
import patterns.decorator.SyrupType;

public class DrinkRequest {

    private final String code;
    // "ESP", "LAT"

    private final boolean withMilk;
    private final MilkType milkType;

    private final boolean withSyrup;
    private final SyrupType syrupType;

    private final boolean withExtraShot;
    private final int quantity;
    private final boolean withWhippedCream;
    private final boolean withCinnamon;

    public DrinkRequest(String code,
                        boolean withMilk,
                        boolean withSyrup,
                        SyrupType syrupType,
                        MilkType milkType,
                        boolean withExtraShot,
                        int quantity,
                        boolean withWhippedCream,
                        boolean withCinnamon) {
        this.code = code;
        this.withMilk = withMilk;
        this.withSyrup = withSyrup;
        this.syrupType = syrupType;
        this.milkType = milkType;
        this.withExtraShot = withExtraShot;
        this.quantity = quantity;
        this.withWhippedCream = withWhippedCream;
        this.withCinnamon = withCinnamon;
    }

    public String getCode() {
        return code;
    }

    public boolean isWithMilk() {
        return withMilk;
    }
    public MilkType getMilkType() {
        return milkType;
    }

    public boolean isWithSyrup() {
        return withSyrup;
    }


    public SyrupType getSyrupType() {
        return syrupType;
    }

    public boolean isWithExtraShot() {
        return withExtraShot;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isWithWhippedCream() {
        return withWhippedCream;
    }

    public boolean isWithCinnamon() {
        return withCinnamon;
    }
}
