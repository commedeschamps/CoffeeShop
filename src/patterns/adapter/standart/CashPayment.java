package patterns.adapter.standart;

public class CashPayment implements  PaymentProcessor {
    @Override
    public void processPayment(double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("You gave no money!");
        }

        System.out.println("Processing cash payment of amount: " + amount);
    }
}
