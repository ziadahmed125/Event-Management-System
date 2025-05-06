package actors;

import core.*;
import models.*;

import java.time.LocalDate;

public class Admin extends User {
    private String role;
    private int workingHours;

    public Admin(String firstName, String lastName, String username, Gender gender, LocalDate dateOfBirth, String address,String email, String password) {
        super(firstName, lastName, username, gender, dateOfBirth, address,email, password);
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nRole: " + role
                + "\nWorking Hours: " + workingHours;
    }

    // Add a new room to the system
    public void addRoom(boolean availability, String roomType, double price) {
        Room newRoom = new Room(availability, roomType, price);
        Database.roomsDB.add(newRoom);
        System.out.println("Room added successfully! ID: " + newRoom.getRoomId());
    }

    // Dashboard methods
    public void showDashboard() {
        int choice =  Utility.promptInt(
                """
                        -- Dashboard --
                        1 - Event Manged
                        2 - Organizers
                        3 - Attendees
                        -->\s
                """
        );

        switch (choice) {
            case 1 -> {}
            case 2 -> this.showOrganizers();
            case 3 -> this.showAttendees();
        }
    }


    public void showCategories() {
        Category.readAll();
    }

    public void editProfile(Admin admin) {
        System.out.println("\n--- Edit Profile ---");
        String newRole = Utility.promptInput("Enter new role: ");
        int newHours = Utility.promptInt("Enter new working hours: ");
        admin.setRole(newRole);
        admin.setWorkingHours(newHours);
        System.out.println("Profile updated successfully!");
    }

    public static boolean usernameExists(String username) {
        return Database.adminsDB.stream()
                .filter(ad -> ad.getUsername() != null)
                .anyMatch(ad -> ad.getUsername().equalsIgnoreCase(username));
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

//    public String showEvents() {
//        if (Database.eventsDB.isEmpty()) {
//            return "No events scheduled.";
//        }
//
//        String string = "";
//        int count = 0;
//        for (Event event : Database.eventsDB) {
//            string += "Event #" + ++count
//                    + "\nID: " + event.getEventId()
//                    + "\nName: " + event.getName()
//                    + "\nOrganizer: " + event.getOrganizer().getUsername()
//                    + "\nRoom: " + event.getRoom().getRoomId()
//                    + "\nDate: " + event.getDateTime()
//                    + "\n-------------------";
//        }
//
//        return string;
//    }
    public String showOrganizers() {
        if (Database.organizersDB.isEmpty()) {
            return "No Organizers registered.";
        }

        String string = "";
        int count = 0;
        for (Organizer organizer : Database.organizersDB) {
            string += "Organizer "+ ++count + ": "
                    + organizer.getFirstName() + ' ' + organizer.getLastName()
                    + " (" + organizer.getUsername() + ") "
                    + "\n-------------------";
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
            string += "Attendee "+ ++count + ": "
                    + attendee.getFirstName() + ' ' + attendee.getLastName()
                    + " (" + attendee.getUsername() + ") "
                    + "\n-------------------";
        }

        return string;
    }

    public void manageRooms() {
        manageRoomsMenu:
        while (true) {
            int choice = Utility.promptInt(
                    """
                            --- Manage Rooms ---
                            1 - Show Rooms
                            2 - Add Room
                            3 - Update Room Availability
                            4 - Back to Admin Menu
                            -->\s
                    """
            );

            switch (choice) {
                case 1 -> this.showRooms();
                case 2 -> {
                    String type = Utility.promptInput("Enter room type: ");
                    double price = Utility.promptDouble("Enter room price: ");
                    this.addRoom(true, type, price);  // assuming true for availability
                }
                case 3 -> {}
                case 4 -> {
                    break manageRoomsMenu;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    public String showRooms() {
        if (Database.roomsDB.isEmpty()) {
            return "There are no rooms available";
        }

        String string = "";
        int count = 0;
        for (Room room : Database.roomsDB) {
            string += "Room #" + ++count
                    + "\nID: " + room.getRoomId()
                    + "\nType: " + room.getRoomType()
                    + "\nPrice: $" + room.getPrice()
                    + "\nAvailable: " + (room.isAvailable() ? "Yes" : "No")
                    + "\n-------------------";
        }

        return string;
    }

    public void manageCategories() {
        manageCategoriesMenu:
        while (true) {
            int choice = Utility.promptInt(
                    """
                            --- Manage Rooms ---
                            1 - Show Categories
                            2 - Add Category
                            3 - Back to Admin Menu
                            -->\s
                    """
            );

            switch (choice) {
                case 1 -> this.showCategories();
                case 2 -> {
                    String name = Utility.promptInput("Enter category Name: ");
                    String desc = Utility.promptInput("Enter category Description: ");
                    String type = Utility.promptInput("Enter category Type: ");
                    this.addCategory(name, desc, type);
                }
                case 3 -> {
                    break manageCategoriesMenu;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
    public void addCategory(String name, String description, String type) {
        Category newCategory = new Category(name, description, type);
        Database.categoriesDB.add(newCategory);
        System.out.println("Category added successfully! under Category name: " + newCategory.getName());
    }

    public void displayProfile(){
        super.displayProfile();
        System.out.println( "Role: " + this.role);
        System.out.println( "Working Hours:  " + this.workingHours);
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
