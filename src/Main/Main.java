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
import models.Gender;
import models.Wallet;

import java.time.LocalDate;
import java.util.Objects;

public class Main extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        Database.attendeesDB.add(new Attendee("Ziad", "Ahmed", "xzxz", Gender.valueOf("MALE"), LocalDate.parse("2000-05-12"), "Cairo", "@", "xzxz1234"));
        Database.adminsDB.add(new Admin("Ziad", "Ahmed", "xzxz", Gender.valueOf("MALE"), LocalDate.parse("2000-05-12"), "Cairo", "@", "xzxz1234"));
        Database.organizersDB.add(new Organizer("Ziad", "Ahmed", "xzxz", Gender.valueOf("MALE"), LocalDate.parse("2000-05-12"), "Cairo", "@", "xzxz1234"));

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
