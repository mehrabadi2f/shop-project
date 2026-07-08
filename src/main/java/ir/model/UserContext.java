package ir.model;

import java.time.LocalDate;

public class UserContext {
    private String nationalCode;
    private LocalDate membershipDate;
    private int subsidyYear;
    private int subsidyMonth;
    private double balance;

    // Constructor
    public UserContext(String nationalCode, LocalDate membershipDate, int subsidyYear, int subsidyMonth, double balance) {
        this.nationalCode = nationalCode;
        this.membershipDate = membershipDate;
        this.subsidyYear = subsidyYear;
        this.subsidyMonth = subsidyMonth;
        this.balance = balance;
    }

    // Getters
    public String getNationalCode() { return nationalCode; }
    public LocalDate getMembershipDate() { return membershipDate; }
    public int getSubsidyYear() { return subsidyYear; }
    public int getSubsidyMonth() { return subsidyMonth; }
    public double getBalance() { return balance; }
}
