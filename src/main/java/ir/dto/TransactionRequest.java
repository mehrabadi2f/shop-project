package ir.dto;


public class TransactionRequest {
    private String action; // مثلا "WITHDRAW" یا "TRANSFER"
    private double amount;
    private String targetAccount;

    // Constructor
    public TransactionRequest(String action, double amount, String targetAccount) {
        this.action = action;
        this.amount = amount;
        this.targetAccount = targetAccount;
    }

    // Getters
    public String getAction() { return action; }
    public double getAmount() { return amount; }
    public String getTargetAccount() { return targetAccount; }
}
