package patterns.adapter.external;

public class KaspiPaymentAPI {
    public String generateQR(double sum) {
        String transactionId = "KSP-" + System.currentTimeMillis();
        System.out.println("Kaspi: Generated QR-code for payment " + sum + " ");
        return transactionId;
    }

    public boolean confirmPayment(String transactionId) {
        System.out.println("Kaspi: Payment confirmation " + transactionId);
        return true;
    }
}