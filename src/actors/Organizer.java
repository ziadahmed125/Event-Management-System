package actors;

import core.Database;
import core.Utility;
import models.Category;
import models.Event;
import models.Gender;
import models.Room;

import java.time.LocalDate;
import java.util.Scanner;

public class Organizer extends User {
    private String[] eventsOrganizing;
    private String[] attendeesOfEvent;

    public Organizer(String firstName, String lastName, String username, Gender gender, LocalDate dateOfBirth, String address, String email, String password) {
        super(firstName, lastName, username, gender, dateOfBirth, address, email, password);
    }

    public void createEvent() {
       Scanner scanner = new Scanner(System.in);

        String name = Utility.promptInput("Event Name: ");
        String desc = Utility.promptInput("Description: ");
        Category selectedCategory = null;
        System.out.println("The Categories available to choose from are: ");
        for (Category c : Database.categoriesDB){
            System.out.println(c.getName() + ", ");
        }
        // Prompt user for input
        System.out.println("\nEnter the Category you want from the above categories: ");
        String catName = scanner.nextLine().trim();
        // Find the selected category
        for (Category c : Database.categoriesDB) {
            if (c.getName().equalsIgnoreCase(catName)) {
                selectedCategory = c;
                break;
            }
        }


        Room selectedRoom = null;
        System.out.println("The Room IDs available to choose from are: ");
        for (Room r : Database.roomsDB){                    //Showing Rooms avail
            System.out.println(r.getRoomId() + ", ");
        }
        // Prompt user for input
        System.out.println("\nEnter the Room id you want from the above Rooms: ");
        int roomID = scanner.nextInt();
        // Find the selected category
        for (Room r : Database.roomsDB) {
            if (r.getRoomId() == roomID) {
                selectedRoom = r;
                break;
            }
        }
        double TPrice =  Utility.promptDouble("Ticket Price will be: ");
        Event newEvent = new Event(name, desc, selectedCategory, selectedRoom, this, TPrice);
    }

    public void displayProfile() {
        super.displayProfile();
    }

    public static boolean usernameExists(String username) {
        return Database.organizersDB.stream()
                .filter(o -> o.getUsername() != null)
                .anyMatch(o -> o.getUsername().equalsIgnoreCase(username));
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

    // Dashboard methods
    public void showDashboard() {
        int choice =  Utility.promptInt(
                """
                        -- Dashboard --
                        1 - Available Rooms
                        2 - Events Organizing
                        3 - Events Attendees
                        -->\s
                """
        );

        switch (choice) {
            case 1 -> Room.availableRooms();
            case 2 -> this.showEventsOrganizing();
            case 3 -> this.showAttendeesOfEvent();
        }
    }

    public void showEventsOrganizing() {
        for (String s : eventsOrganizing) {
            System.out.println(s + ", ");
        }
    }
    public void showAttendeesOfEvent() {
        for (String s : attendeesOfEvent) {
            System.out.println(s + ", ");
        }
    }
}
