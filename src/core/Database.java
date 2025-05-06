package core;

import models.*;
import actors.*;

import java.io.*;
import java.util.ArrayList;

public class Database {
    private static final String DATA_DIR = "data"; // folder to store files

    public static ArrayList<Attendee> attendeesDB = new ArrayList<>();
    public static ArrayList<Admin> adminsDB = new ArrayList<>();
    public static ArrayList<Organizer> organizersDB = new ArrayList<>();
    public static ArrayList<Event> eventsDB = new ArrayList<>();
    public static ArrayList<Room> roomsDB = new ArrayList<>();
    public static ArrayList<Wallet> walletsDB = new ArrayList<>();
    public static ArrayList<Category> categoriesDB = new ArrayList<>();

    public static void removeUser(String identifier, String list) {
        switch (list) {
            case "Admin" -> {
                for(Admin admin : adminsDB) {
                    if(admin.getUsername().equals(identifier))  {
                        adminsDB.remove(admin);
                        break;
                    }
                }
            }
            case "Attendee" -> {
                for(Attendee attendee : attendeesDB) {
                    if(attendee.getUsername().equals(identifier))  {
                        adminsDB.remove(attendee);
                        break;
                    }
                }
            }
            case "Organizer" -> {
                for(Organizer organizer : organizersDB) {
                    if(organizer.getUsername().equals(identifier))  {
                        adminsDB.remove(organizer);
                        break;
                    }
                }
            }
            case "Room" -> {
                for(Room room : roomsDB) {
                    if(room.getRoomId() == Integer.parseInt(identifier)) {
                        roomsDB.remove(room);
                        break;
                    }
                }
            }
            default -> {
                break;
            }
        }
    }
}
