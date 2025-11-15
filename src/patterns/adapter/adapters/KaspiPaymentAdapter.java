package patterns.adapter.adapters;

import patterns.adapter.external.KaspiPaymentAPI;
import patterns.adapter.standart.PaymentProcessor;

public class KaspiPaymentAdapter implements PaymentProcessor {
    private final KaspiPaymentAPI kaspiPayment;

    public KaspiPaymentAdapter(KaspiPaymentAPI kaspiPayment) {
        this.kaspiPayment = kaspiPayment;
    }

    @Override
    public void processPayment(double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be a positive value. Received: " + amount);
        }
        String transactionId = kaspiPayment.generateQR(amount);
        kaspiPayment.confirmPayment(transactionId);
    }
}
