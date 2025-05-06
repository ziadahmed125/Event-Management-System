package core;

import models.*;
import actors.*;

public class Menu {
    // Admin Menu Functions
    public static void adminMenu(Admin admin) {
        adminMenu:
        while (true) {
            int choice = Utility.promptInt(
                    """
                            -- Admin Menu --
                            1 - View Profile
                            2 - Edit Profile
                            3 - Show Dashboard
                            4 - Manage Rooms
                            5 - Manage Categories
                            6 - Logout
                            -->\s
                    """
            );

            switch (choice) {
                case 1 -> admin.displayProfile();
                case 2 -> admin.editProfile(admin);
                case 3 -> admin.showDashboard();
                case 4 -> admin.manageRooms();
                case 5 -> admin.manageCategories();
                case 6 -> {
                    System.out.println("Logging out...");
                    break adminMenu;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Attendee Menu Functions
    public static void attendeeMenu(Attendee attendee) {
        attendeeMenu:
        while (true) {
            int choice = Utility.promptInt(
                    """
                            -- Attendee Menu --
                            1 - View Profile
                            2 - Edit Profile
                            3 - Display Dashboard
                            5 - Logout
                            -->\s
                    """
            );

            switch (choice) {
                case 1 -> attendee.displayProfile();
                case 2 -> attendee.editProfile();
                case 3 -> attendee.displayDashboard();
                case 4 -> {
                    System.out.println("Logging out...");
                    break attendeeMenu;
                }
                default -> System.out.println("Invalid Input! Try again...");
            }
        }
    }

    // Organizer Menu Functions
    public static void organizerMenu(Organizer organizer) {
        organizerMenu:
        while (true) {
            int choice = Utility.promptInt(
                    """
                            -- Organizer Menu --
                            1 - View Profile
                            2 - Edit Profile
                            3 - Create Event
                            4 - Rent Room
                            5 - Dashboard
                            6 - Logout
                            -->\s
                    """
            );

            switch (choice) {
                case 1 -> organizer.displayProfile();
                case 2 -> organizer.editProfile();
                case 3 -> organizer.createEvent();
                case 4 -> Room.availableRooms();
                case 5 -> organizer.showDashboard();
                case 6 -> {
                    System.out.println("Logging out...");
                    break organizerMenu;
                }
                default -> System.out.println("Invalid Input! Try again...");
            }
        }
    }
}