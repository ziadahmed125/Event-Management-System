package models;

import core.Database;

import java.util.ArrayList;

public class Room {
    private static int idCounter = 1; // Static counter shared by all Room instances

    private final int roomId;
    private boolean availability;
    private String roomType;
    private double price;

    public Room(boolean availability, String roomType, double price){
        this.roomId = idCounter++; // Automatically assign and then increment
        this.availability = availability;
        this.roomType = roomType;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room " + roomId;
    }

    // Getters and setters
    public int getRoomId() {
        return roomId;
    }

    public String getRoomType() {
        return roomType;
    }
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
    public boolean getAvailability() {
        return availability;
    }

    public String getRoomInfo() {
        return "Room ID: " + this.roomId
                + "\nRoom Type: " + this.roomType
                + "\nRoom Price: $" + this.price;
    }

    public static void updateRoomAvailability(int roomId, boolean availability) {
        for (Room room : Database.roomsDB) {
            if (room.getRoomId() == roomId) {
                room.setAvailability(availability); // Directly update the room
                return; // Room updated successfully
            }
        }
    }


    public static String availableRooms() {
        if (Database.roomsDB.isEmpty()) {
            return "No Available Rooms!";
        }

        String string = "";
        int count = 0;
        for (Room room : Database.roomsDB) {
            if (room.getAvailability()) {
                string += "Room #" + ++count
                        + "\nID: " + room.getRoomId()
                        + "\nType: " + room.getRoomType()
                        + "\nPrice: $" + room.getPrice()
                        + "\n-------------------\n";
            }
        }

        if(!string.isEmpty())
            return string;
        else
            return "No Available Rooms!";
    }

    public static ArrayList<Room> getAvailableRooms() {
        ArrayList<Room> rooms = new ArrayList<>();

        for(Room room : Database.roomsDB) {
            if (room.getAvailability())
                rooms.add(room);
        }

        return rooms;
    }

    public void delete() {
        Database.roomsDB.remove(this);
    }
}
