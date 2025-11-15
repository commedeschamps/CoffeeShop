package patterns.adapter.external;


public class HalykPaymentAPI {
    public void initiateTransaction(double amount) {
        System.out.println("Halyk Bank: Transaction initiated for amount " +
                amount);
    }

    public boolean verifyTransaction(double amount) {
        System.out.println("Halyk Bank: Transaction verification for amount " + amount);
        return true;
    }
}