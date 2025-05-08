package models;

public class Wallet {
    private double balance;

    public Wallet() {
        this.balance = 0;
    }

    public Wallet(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Add funds to the wallet
    public void addFunds(double amount) {
       balance += amount;
    }

    // Deduct funds from the wallet (for ticket purchase, etc.)
    public void deductFunds(double amount) {
        balance -= amount;
    }
}
