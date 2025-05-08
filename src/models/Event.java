package models;

import core.*;
import actors.*;
import interfaces.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event implements Imanageable {
    private static int idCounter = 1; // For auto-incrementing ID

    private String eventId;
    private String name;
    private String description;
    private Category category;
    private LocalDateTime dateTime;
    private Room room;
    private Organizer organizer;
    private double ticketPrice;
    private ArrayList<Attendee> attendees;

    public Event(String name, String description, Category category, Room room, Organizer organizer, double ticketPrice) {
        this.eventId = "EVT#" + idCounter++; // Auto-generated ID
        this.name = name;
        this.description = description;
        this.category = category;
        this.room = room;
        this.organizer = organizer;
        this.ticketPrice = ticketPrice;
        this.attendees = new ArrayList<>();

        Database.eventsDB.add(this);
    }

    public static String showEvents() {
        if (Database.eventsDB.isEmpty()) {
            return "No Scheduled Events!";
        }

        String string = "";
        int count = 0;
        for (Event event : Database.eventsDB) {
            string += "Event #" + ++count
                    + "\nID: " + event.getEventId()
                    + "\nName: " + event.getName()
                    + "\nOrganizer: " + event.getOrganizer().getUsername()
                    + "\nRoom: " + event.getRoom().getRoomId()
                    + "\nDate: " + event.getDateTime()
                    + "\n-------------------\n";
        }

        return string;
    }

    // CRUD Implementation
    @Override
    public void create() {
        Database.eventsDB.add(this);
        System.out.println("Event created successfully!");
    }

    @Override
    public void read() {
        System.out.println("\nEvent Details:");
        System.out.println("ID: " + eventId);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Category: " + category.getName());
        System.out.println("Room: " + room.getRoomId());
        System.out.println("Organizer: " + organizer.getUsername() );
        System.out.println("Ticket Price: " + ticketPrice);
    }

    public static void readAll() {
        System.out.println("\nAll Events:");
        if (Database.eventsDB.isEmpty()) {
            System.out.println("There are no events yet");
        } else {
            for (Event event : Database.eventsDB) {
                event.read();
                System.out.println("...................................");
            }
        }
    }

    @Override
    public void update() {
        for (Event event : Database.eventsDB) {
            if (event.eventId.equals(this.eventId)) {
                event.name = this.name;
                event.description = this.description;
                event.category = this.category;
                event.dateTime = this.dateTime;
                event.room = this.room;
                event.organizer = this.organizer;
                event.ticketPrice = this.ticketPrice;
                System.out.println("Event updated successfully!");
                return;
            }
        }
        System.out.println("Event not found");
    }

    @Override
    public void delete() {
        for (int i = 0; i < Database.eventsDB.size(); i++) {
            if (Database.eventsDB.get(i).eventId.equals(this.eventId)) {
                Database.eventsDB.remove(i);
                System.out.println("Event deleted successfully!");
                return;
            }
        }
        System.out.println("Event not found for deletion!");
    }

    public void addAttendee(Attendee attendee) {
        attendees.add(attendee);
    }

    public static Event getEvent(String id) {
        for(Event event : Database.eventsDB) {
            if (event.getEventId().equals(id)) {
                return event;
            }
        }

        return null;
    }

    // Getters
    public String getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Room getRoom() {
        return room;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public ArrayList<Attendee> getAttendees() {
        return attendees;
    }
}