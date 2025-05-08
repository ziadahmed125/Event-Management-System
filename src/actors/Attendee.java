package actors;

import core.Database;
import core.Utility;
import models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class Attendee extends User {

    private Wallet wallet; // Composition: Attendee has-a Wallet
    private ArrayList<String> interests = new ArrayList<>();
    private ArrayList<String> tickets = new ArrayList<>();

    public Attendee(String firstName, String lastName, String username, Gender gender, LocalDate dateOfBirth, String address,String email, String password) {
        super(firstName, lastName, username, gender, dateOfBirth, address, email, password);
        this.wallet = new Wallet(0.0); // initialize wallet with 0 balance
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

    public void displayDashboard() {
        int choice = Utility.promptInt(
                """
                        -- Dashboard --
                        1 - Available Events
                        2 - Buy Ticket
                        -->\s
                """
        );

        switch (choice) {
            case 1 -> Event.readAll();
            case 2 -> buyTicket();
        }
    }

    public void buyTicket() {
        String id = Utility.promptInput("Enter Event ID to buy ticket: ");
        for (Event event : Database.eventsDB) {
            if (event.getEventId().equals(id)) {
                if (getBalance() >= event.getTicketPrice()) {
                    setBalance(getBalance() - event.getTicketPrice());
                    event.addAttendee(this);
                    System.out.println("Ticket purchased!");
                } else {
                    System.out.println("Insufficient balance.");
                }
            }
        }
        System.out.println("Event not found.");
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
}
