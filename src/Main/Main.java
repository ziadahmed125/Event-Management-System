package Main;

import actors.Admin;
import actors.Attendee;
import actors.Organizer;
import core.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Main extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        Database.attendeesDB.add(new Attendee("Ziad", "Ahmed", "XED", Gender.valueOf("Male"), LocalDate.parse("2007-05-12"), "Cairo", "@", ""));
        Database.organizersDB.add(new Organizer("Ziad", "Ahmed", "XED", Gender.valueOf("Male"), LocalDate.parse("2007-05-12"), "Cairo", "@", ""));
        Database.adminsDB.add(new Admin("Ziad", "Ahmed", "XED", Gender.valueOf("Male"), LocalDate.parse("2007-05-12"), "Cairo", "@", "", "xxx", 8));

        // Adding Attendees
        Database.attendeesDB.add(new Attendee("John", "Doe", "JDoe", Gender.valueOf("Male"), LocalDate.parse("1995-08-23"), "New York", "john.doe@example.com", "password123"));
        Database.attendeesDB.add(new Attendee("Alice", "Smith", "ASmith", Gender.valueOf("Female"), LocalDate.parse("1990-11-15"), "London", "alice.smith@example.com", "password456"));
        Database.attendeesDB.add(new Attendee("Bob", "Johnson", "BJohnson", Gender.valueOf("Male"), LocalDate.parse("1987-02-02"), "Paris", "bob.johnson@example.com", "password789"));

// Adding Organizers
        Database.organizersDB.add(new Organizer("Eve", "Davis", "EDavis", Gender.valueOf("Female"), LocalDate.parse("1982-04-05"), "Berlin", "eve.davis@example.com", "securepass1"));
        Database.organizersDB.add(new Organizer("Charlie", "Brown", "CBrown", Gender.valueOf("Male"), LocalDate.parse("1992-07-14"), "Toronto", "charlie.brown@example.com", "securepass2"));
        Database.organizersDB.add(new Organizer("Dana", "Lee", "DLee", Gender.valueOf("Female"), LocalDate.parse("1985-03-19"), "Sydney", "dana.lee@example.com", "securepass3"));

// Adding Admins
        Database.adminsDB.add(new Admin("Grace", "Clark", "GClark", Gender.valueOf("Female"), LocalDate.parse("1980-10-30"), "Tokyo", "grace.clark@example.com", "adminpass1", "SuperAdmin", 10));
        Database.adminsDB.add(new Admin("Hannah", "Martinez", "HMartinez", Gender.valueOf("Female"), LocalDate.parse("1975-05-13"), "Dubai", "hannah.martinez@example.com", "adminpass2", "Manager", 15));
        Database.adminsDB.add(new Admin("Iris", "Garcia", "IGarcia", Gender.valueOf("Female"), LocalDate.parse("1983-09-25"), "Los Angeles", "iris.garcia@example.com", "adminpass3", "Staff", 8));

// Adding Events
        Database.eventsDB.add(new Event("Wedding Celebration", "An elegant wedding event", new Category("Wedding", "Celebrate love"),
                new Room(true, "Banquet Hall", 250.0),
                new Organizer("Eve", "Davis", "EDavis", Gender.valueOf("Female"), LocalDate.parse("1982-04-05"), "Berlin", "eve.davis@example.com", "securepass1"), 500.0));

        Database.eventsDB.add(new Event("Music Concert", "A live concert with top artists", new Category("Concert", "Live music performance"),
                new Room(true, "Concert Hall", 500.0),
                new Organizer("Charlie", "Brown", "CBrown", Gender.valueOf("Male"), LocalDate.parse("1992-07-14"), "Toronto", "charlie.brown@example.com", "securepass2"), 100.0));

// Adding Rooms
        Database.roomsDB.add(new Room(true, "Conference Room", 150.0));
        Database.roomsDB.add(new Room(false, "Meeting Room", 80.0));
        Database.roomsDB.add(new Room(true, "Exhibition Hall", 200.0));

// Adding Categories
        Database.categoriesDB.add(new Category("Seminar", "A professional seminar"));
        Database.categoriesDB.add(new Category("Workshop", "Hands-on practical learning"));
        Database.categoriesDB.add(new Category("Conference", "A large conference for networking"));


        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/views/SignUpView.fxml")));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Event Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
