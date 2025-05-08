package actors;

import core.Database;
import core.Utility;
import models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class Attendee extends User {

    private Wallet wallet;
    private ArrayList<String> interests = new ArrayList<>();
    private ArrayList<String> tickets = new ArrayList<>();

    public Attendee(String firstName, String lastName, String username, Gender gender, LocalDate dateOfBirth, String address,String email, String password) {
        super(firstName, lastName, username, gender, dateOfBirth, address, email, password);
        this.wallet = new Wallet(0.0); // initialize wallet with 0 balance
    }

    public static Attendee getLoggedInUser(String email, String password) {
        for (Attendee a : Database.attendeesDB) {
            if ((a.getEmail() != null && a.getPassword() != null) &&
                    (a.getEmail().equalsIgnoreCase(email) &&
                            a.getPassword().equals(password))) {
                return a;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nInterests: " + getInterests();
    }

    public static boolean usernameExists(String username) {
        return Database.attendeesDB.stream()
                .filter(a -> a.getUsername() != null)
                .anyMatch(a -> a.getUsername().equalsIgnoreCase(username));
    }

    // Wallet
    public String getBalanceString() {
        return "Balance: $" + wallet.getBalance();
    }
    public double getBalance() {
        return wallet.getBalance();
    }
    public void setBalance (double balance){
        wallet.setBalance(balance);
    }
    public void addFunds(double amount) {
        wallet.addFunds(amount);
    }
    public void deductFunds(double amount) {
        wallet.deductFunds(amount);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setInterests(String interest){
        interests.add(interest);
    }
    public String getInterests(){
        String s ="";

        for(String interest : interests){
            s += "(" + interest + ") ";
        }

        return s;
    }

    public boolean isInterestUnique (String s) {
        for(String interest : interests) {
            if (s.equals(interest))
                return false;
        }

        return true;
    }
    public int numOfInterests() {
        return interests.size();
    }

    public void setTickets(String ticket){
        tickets.add(ticket);
    }
    public String getTickets(){
        String s ="";

        for(String ticket : tickets){
            s += "(" + ticket + ") ";
        }

        return s;
    }
}
