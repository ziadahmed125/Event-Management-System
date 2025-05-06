package core;

import actors.*;
import models.*;
import java.time.LocalDate;

public class UserService {
//    // registration
//    public static Admin registerAdmin() {
//        String username = Utility.promptInput("Username: ");
//        String email = Utility.promptInput("Email: ");
//        String password = Utility.promptPassword("Password (min 8 characters): ");
//        LocalDate dob = Utility.promptDate("Date of Birth (yyyy-MM-dd): ");
//        String role = Utility.promptInput("Role: ");
//        int workingHours = Utility.promptWorkingHours("Working Hours: ");
//
//        Admin admin = new Admin(username, email, password, dob, role, workingHours);
//        Database.adminsDB.add(admin);
//        System.out.println("Registered as an Admin Successfully!");
//        return admin;
//    }
//    public static Organizer registerOrganizer() {
//        String username = Utility.promptInput("Username: ");
//        String email = Utility.promptInput("email: ");
//        String password = Utility.promptPassword("Password (min 8 characters): ");
//        LocalDate dob = Utility.promptDate("Date of Birth (yyyy-MM-dd): ");
//
//        Organizer organizer = new Organizer(username, email, password, dob);
//        Database.organizersDB.add(organizer);
//        System.out.println("Registered as an Organizer Successfully!");
//        return organizer;
//    }
//    public static Attendee registerAttendee() {
//        String username = Utility.promptInput("Username: ");
//        String email = Utility.promptInput("Email: ");
//        String password = Utility.promptPassword("Password (min 8 characters): ");
//        LocalDate dob = Utility.promptDate("Date of Birth (yyyy-MM-dd): ");
//        String address = Utility.promptInput("Address: ");
//        Gender gender = Utility.promptGender();
//        String[] interests = Utility.promptInputInterests();
//
//        Attendee attendee = new Attendee(username, email, password, dob, address, gender, interests);
//        Database.attendeesDB.add(attendee);
//        System.out.println("Registered as an Attendee Successfully!");
//        return attendee;
//    }
//
//    // login
//    public static User login() {
//        String username = Utility.promptInput("Email: ");
//        String password = Utility.promptPassword("Password: ");
//        int role = Utility.promptInt("-- Login As --\n1 - Admin\n2 - Organizer\n3 - Attendee\n--> ");
//
//        switch (role) {
//            case 1:
//                for (Admin admin : Database.adminsDB) {
//                    if (admin.login(username, password)) {
//                        System.out.println("Logged in as Admin successfully!");
//                        return admin;
//                    }
//                }
//                break;
//            case 2:
//                for (Organizer organizer : Database.organizersDB) {
//                    if (organizer.login(username, password)) {
//                        System.out.println("Logged in as Organizer successfully!");
//                        return organizer;
//                    }
//                }
//                break;
//            case 3:
//                for (Attendee attendee : Database.attendeesDB) {
//                    if (attendee.login(username, password)) {
//                        System.out.println("Logged in as Attendee successfully!");
//                        return attendee;
//                    }
//                }
//                break;
//            default:
//                System.out.println("Invalid role choice.");
//                return null;
//        }
//
//        System.out.println("Login failed. Invalid credentials or user not found.");
//        return null;
//    }
}
