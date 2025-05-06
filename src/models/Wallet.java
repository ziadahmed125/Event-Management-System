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
        if (amount > 0) {
            balance += amount;
            System.out.println("Funds added: " + amount);
        } else {
            System.out.println("Invalid amount.");
        }
    }

    // Deduct funds from the wallet (for ticket purchase, etc.)
    public boolean deductFunds(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Funds deducted: " + amount);
            return true;
        } else {
            System.out.println("Insufficient balance.");
            return false;
        }
    }
}
