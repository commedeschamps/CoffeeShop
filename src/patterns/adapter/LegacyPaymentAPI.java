package patterns.adapter;

// some external or legacy payment API, we cannot change this class

public class LegacyPaymentAPI {

    public void makeTransaction(int amountInCents) {
        System.out.println("[LegacyPaymentApi] Transaction for " + amountInCents + " ₸ processed.");
    }
}
