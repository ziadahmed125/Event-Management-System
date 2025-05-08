package actors;

import core.*;
import models.*;

import java.time.LocalDate;

public class Admin extends User {
    private String role;
    private int workingHours;

    public Admin(String firstName, String lastName, String username, Gender gender, LocalDate dateOfBirth, String address,String email, String password) {
        super(firstName, lastName, username, gender, dateOfBirth, address, email, password);
        this.role = "null";
        this.workingHours = 0;
    }

    // for dummy data
    public Admin(String firstName, String lastName, String username, Gender gender, LocalDate dateOfBirth, String address,String email, String password, String role, int workingHours) {
        super(firstName, lastName, username, gender, dateOfBirth, address, email, password);
        this.role = role;
        this.workingHours = workingHours;
    }

    public static Admin getLoggedInUser(String email, String password) {
        for (Admin admin : Database.adminsDB) {
            if (admin.getEmail() != null && admin.getPassword() != null &&
                    admin.getEmail().equalsIgnoreCase(email) &&
                    admin.getPassword().equals(password)) {
                return admin;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nRole: " + role
                + "\nWorking Hours: " + workingHours;
    }

    public static boolean usernameExists(String username) {
        return Database.adminsDB.stream()
                .filter(ad -> ad.getUsername() != null)
                .anyMatch(ad -> ad.getUsername().equalsIgnoreCase(username));
    }

    public String showOrganizers() {
        if (Database.organizersDB.isEmpty()) {
            return "No Organizers registered.";
        }

        String string = "";
        int count = 0;
        for (Organizer organizer : Database.organizersDB) {
            string += "Organizer #"+ ++count + ": "
                    + organizer.getFirstName() + ' ' + organizer.getLastName()
                    + " (" + organizer.getUsername() + ") "
                    + "\n-------------------\n";
        }

        return string;
    }

    public String showAttendees() {
        if (Database.attendeesDB.isEmpty()) {
            return "No attendees registered.";
        }

        String string = "";
        int count = 0;
        for (Attendee attendee : Database.attendeesDB) {
            string += "Attendee #"+ ++count + ": "
                    + attendee.getFirstName() + ' ' + attendee.getLastName()
                    + " (" + attendee.getUsername() + ") "
                    + "\n-------------------\n";
        }

        return string;
    }

    public String showRooms() {
        if (Database.roomsDB.isEmpty()) {
            return "There are no rooms available!";
        }

        String string = "";
        int count = 0;
        for (Room room : Database.roomsDB) {
            string += "Room #" + ++count
                    + "\nID: " + room.getRoomId()
                    + "\nType: " + room.getRoomType()
                    + "\nPrice: $" + room.getPrice()
                    + "\nAvailable: " + (room.getAvailability() ? "Yes" : "No")
                    + "\n-------------------\n";
        }

        return string;
    }

    public String showCategories() {
        if (Database.categoriesDB.isEmpty()) {
            return "There are no Categories available!";
        }

        String string = "";
        int count = 0;
        for (Category category : Database.categoriesDB) {
            string += "Category #" + ++count
                    + "\nName: " + category.getName()
                    + "\nDescription: " + category.getDescription()
                    + "\n-------------------\n";
        }

        return string;
    }

    // Getters and setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }
}
