package actors;

import core.Database;
import core.Utility;
import models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Organizer extends User {
    private Wallet wallet;
    private ArrayList<Event> eventsOrganizing = new ArrayList<>();
    private ArrayList<Room> roomsRented = new ArrayList<>();

    public Organizer(String firstName, String lastName, String username, Gender gender, LocalDate dateOfBirth, String address, String email, String password) {
        super(firstName, lastName, username, gender, dateOfBirth, address, email, password);
        this.wallet = new Wallet(0.0); // initialize wallet with 0 balance
    }

    public static Organizer getLoggedInUser(String email, String password) {
        for (Organizer o : Database.organizersDB) {
            if ((o.getEmail() != null && o.getPassword() != null) &&
                    (o.getEmail().equalsIgnoreCase(email) &&
                            o.getPassword().equals(password))) {
                return o;
            }
        }

        return null;
    }

    public static boolean usernameExists(String username) {
        return Database.organizersDB.stream()
                .filter(o -> o.getUsername() != null)
                .anyMatch(o -> o.getUsername().equalsIgnoreCase(username));
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

    public String getEventsOrganizingString() {
        if (eventsOrganizing.isEmpty()) {
            return "No Available Events!";
        }

        String string = "";
        int count = 0;
        for (Event event : eventsOrganizing) {
            string += "Event #" + ++count
                    + "\nID: " + event.getEventId()
                    + "\nName: " + event.getName()
                    + "\nOrganizer: " + event.getOrganizer().getUsername()
                    + "\nRoom: " + event.getRoom().getRoomId()
                    + "\nDate: " + event.getDateTime()
                    + "\n-------------------\n";
        }

        if(!string.isEmpty())
            return string;
        else
            return "No Available Rooms!";
    }
    public ArrayList<String> getEventsOrganizingNames() {

        ArrayList<String> events = new ArrayList<>();
        for(Event event : eventsOrganizing) {
            events.add(event.getName());
        }

        return events;
    }
    public String getEventsOrganizingAttendees(String s) {
        Event event = null;

        for(Event e : eventsOrganizing) {
            if(e.getName().equals(s)) {
                event = e;
            }
        }

        if(event.getAttendees() == null) return "No attendees yet!";

        String attendees = "";
        int count = 0;
        for(Attendee attendee : event.getAttendees()) {
            attendees += "Attendee #"+ ++count + ": "
                    + attendee.getFirstName() + ' ' + attendee.getLastName()
                    + " (" + attendee.getUsername() + ") "
                    + "\n-------------------\n";
        }

        return attendees;
    }

    public ArrayList<Room> getRoomsRented() {
        return roomsRented;
    }
    public void setRoomsRented(Room room) {
        roomsRented.add(room);
    }
}
