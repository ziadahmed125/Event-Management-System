package actors;

import core.Database;
import core.Utility;
import models.*;

import java.time.LocalDate;
import java.util.Arrays;

public class Attendee extends User {

    private Wallet wallet; // Composition: Attendee has-a Wallet
    private String[] interests;

    public Attendee(String firstName, String lastName, String username, Gender gender, LocalDate dateOfBirth, String address,String email, String password) {
        super(firstName, lastName, username, gender, dateOfBirth, address,email, password);
        this.wallet = new Wallet(0.0); // initialize wallet with 0 balance
    }

    public void displayProfile(){
        super.displayProfile();
        System.out.println( "Address: " + this.address);
        System.out.println( "Balance: $" + this.getBalance());
        System.out.println( "Interests: " + Arrays.toString(interests));
    }
    public void editProfile() {
        super.editProfile();
        setAddress(Utility.promptInput("New Address"));
        System.out.println("Profile updated successfully!");
    }

    public static boolean usernameExists(String username) {
        return Database.attendeesDB.stream()
                .filter(a -> a.getUsername() != null)
                .anyMatch(a -> a.getUsername().equalsIgnoreCase(username));
    }

    public static Attendee getLoggedInUser(String email, String password) {
        for (Attendee a : Database.attendeesDB) {
            if (a.getEmail() != null && a.getPassword() != null &&
                    a.getEmail().equalsIgnoreCase(email) &&
                    a.getPassword().equals(password)) {
                return a;
            }
        }
        return null;
    }

    // --- Wallet Delegation ---
    public double getBalance() {
        return wallet.getBalance();
    }
    public void addFunds(double amount) {
        wallet.addFunds(amount);
    }
    public boolean deductFunds(double amount) {
        return wallet.deductFunds(amount);
    }
    public Wallet getWallet() {
        return wallet;
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
                if (getWallet().getBalance() >= event.getTicketPrice()) {
                    getWallet().setBalance(getWallet().getBalance() - event.getTicketPrice());
                    event.addAttendee(this);
                    System.out.println("Ticket purchased!");
                } else {
                    System.out.println("Insufficient balance.");
                }
            }
        }
        System.out.println("Event not found.");
    }

    public void setInterests(String[] interests) {
        this.interests = interests;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
