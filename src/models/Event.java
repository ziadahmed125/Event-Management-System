package models;

import core.*;
import actors.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {
    private static int idCounter = 1; // For auto-incrementing ID

    private String eventId;
    private String name;
    private String description;
    private Category category;
    private LocalDate dateTime;
    private Room room;
    private Organizer organizer;
    private double ticketPrice;
    private ArrayList<Attendee> attendees;

    public Event() {
        this.eventId = "EVT#" + idCounter++;
    }

    public Event(String name, String description, Category category, Room room, Organizer organizer, double ticketPrice) {
        this.eventId = "EVT#" + idCounter++; // Auto-generated ID
        this.name = name;
        this.description = description;
        this.category = category;
        this.room = room;
        this.organizer = organizer;
        this.ticketPrice = ticketPrice;
        this.attendees = new ArrayList<>();
    }

    @Override
    public String toString() {
        return this.getName();
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

    public void create() {
        Database.eventsDB.add(this);
    }

    public void read() {
    }

    public void update() {
    }

    public void delete() {
        Database.eventsDB.remove(this);
    }

    public static Event getEvent(String id) {
        for(Event event : Database.eventsDB) {
            if (event.getEventId().equals(id)) {
                return event;
            }
        }

        return null;
    }

    // Getters and Setters
    public String getEventId() {
        return eventId;
    }
    public void setEventId (String eventId) {
        this.eventId = eventId;
    }
    public String getName() {
        return name;
    }
    public void setName (String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription (String description) {
        this.description = description;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory (Category category) {
        this.category = category;
    }
    public Room getRoom() {
        return room;
    }
    public void setRoom (Room room) {
        this.room = room;
    }
    public LocalDate getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }
    public Organizer getOrganizer() {
        return organizer;
    }
    public void setOrganizer (Organizer organizer) {
        this.organizer = organizer;
    }
    public double getTicketPrice() {
        return ticketPrice;
    }
    public void setTicketPrice (Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void addAttendee(Attendee attendee) {
        attendees.add(attendee);
    }
    public ArrayList<Attendee> getAttendees() {
        return attendees;
    }
}