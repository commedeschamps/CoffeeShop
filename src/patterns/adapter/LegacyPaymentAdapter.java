package patterns.adapter;

public class LegacyPaymentAdapter implements PaymentProcessor {

    private final LegacyPaymentAPI api;

    public LegacyPaymentAdapter(LegacyPaymentAPI api) {
        this.api = api;
    }

    @Override
    public boolean processPayment(double amount) {
        int tenge = (int) Math.round(amount);
        api.makeTransaction(tenge);
        return true;
    }
}
