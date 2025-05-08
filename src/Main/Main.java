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
import java.util.Objects;

public class Main extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        Database.attendeesDB.add(new Attendee("Ziad", "Ahmed", "XED", Gender.valueOf("Male"), LocalDate.parse("2007-05-12"), "Cairo", "@", ""));
        Database.organizersDB.add(new Organizer("Ziad", "Ahmed", "XED", Gender.valueOf("Male"), LocalDate.parse("2007-05-12"), "Cairo", "@", ""));
        Database.adminsDB.add(new Admin("Ziad", "Ahmed", "XED", Gender.valueOf("Male"), LocalDate.parse("2007-05-12"), "Cairo", "@", ""));
        new Event("Birthday Party", "", new Category("", "", ""),
                new Room(true, "", 100.0),
                new Organizer("Ziad", "Ahmed", "XED", Gender.valueOf("Male"),
                LocalDate.parse("2007-05-12"), "Cairo", "@", ""), 200.0);

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
