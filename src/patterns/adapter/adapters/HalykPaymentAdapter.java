package patterns.adapter.adapters;
import patterns.adapter.external.*;
import patterns.adapter.standart.PaymentProcessor;

public class HalykPaymentAdapter implements PaymentProcessor {
    private HalykPaymentAPI halykPayment;

    public HalykPaymentAdapter(HalykPaymentAPI halykPayment) {
        this.halykPayment = halykPayment;
    }

    @Override
    public void  processPayment(double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        halykPayment.initiateTransaction(amount);
        halykPayment.verifyTransaction(amount);
    }
}
