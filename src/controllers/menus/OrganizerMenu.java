package controllers.menus;

import actors.Admin;
import actors.Organizer;
import core.Database;
import core.Utility;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Gender;
import models.Room;

import java.io.IOException;
import java.time.LocalDate;

public class OrganizerMenu {
    private Organizer loggedInOrganizer;

    // Panes
    @FXML private StackPane rootPane;
    // Main Menu
    @FXML private VBox mainMenuPane;
    // Profile
    @FXML private VBox profilePane;
    @FXML private VBox editProfilePane;
    // Dashboard
    @FXML private VBox showDashboardPane;
    @FXML private VBox availableRoomsPane;
    @FXML private VBox eventsOrganizingPane;
    @FXML private VBox eventsAttendeesPane;
    // Create Event
    @FXML private VBox createEventPane;
    // Rent Room
    @FXML private VBox rentRoomPane;

    // Main Menu
    @FXML
    private Label welcomeLabel;

    // profile
    @FXML private Label profileInfo;

    // edit profile
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private ComboBox<Gender> genderComboBox;
    @FXML private DatePicker dobField;
    @FXML private TextField addressField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label firstNameErrorLabel;
    @FXML private Label lastNameErrorLabel;
    @FXML private Label usernameErrorLabel;
    @FXML private Label dobErrorLabel;
    @FXML private Label addressErrorLabel;
    @FXML private Label emailErrorLabel;
    @FXML private Label passwordErrorLabel;
    @FXML private Label confirmPasswordErrorLabel;

    // dashboard
    @FXML private Label allAvailableRooms;
    @FXML private Label allEventsOrganizing;
    @FXML private Label allEventsAttendees;


    public void setLoggedInOrganizer(Organizer organizer) {
        this.loggedInOrganizer = organizer;

        switchPane(mainMenuPane);
    }

    private void switchPane(Pane target) {
        for (Node child : rootPane.getChildren()) {
            child.setVisible(false);
            child.setManaged(false);
        }
        target.setVisible(true);
        target.setManaged(true);
    }

    @FXML
    public void initialize() {
        genderComboBox.setItems(FXCollections.observableArrayList(Gender.values()));
    }

    public void back(ActionEvent event) {
        switchPane(mainMenuPane);
    }

    // profile
    public void viewProfile(ActionEvent event) {
        switchPane(profilePane);
        profileInfo.setText(loggedInOrganizer.toString());
    }

    // edit profile
    public void editProfile(ActionEvent event) {
        switchPane(editProfilePane);

        firstNameField.setPromptText(loggedInOrganizer.getFirstName());
        lastNameField.setPromptText(loggedInOrganizer.getLastName());
        usernameField.setPromptText(loggedInOrganizer.getUsername());
        genderComboBox.setValue(loggedInOrganizer.getGender());
        dobField.setValue(loggedInOrganizer.getDateOfBirth());
        addressField.setPromptText(loggedInOrganizer.getAddress());
        emailField.setPromptText(loggedInOrganizer.getEmail());
        passwordField.setPromptText("New Password");
        confirmPasswordField.setPromptText("Confirm New Password");
    }
    public boolean validateFields() {
        boolean valid = true;

        // Reset styles and labels
        clearValidationStyles();

        // First Name
        if (firstNameField.getText().trim().isEmpty());
        else if (firstNameField.getText().trim().equals(loggedInOrganizer.getFirstName())) {
            firstNameField.getStyleClass().add("error-field");
            firstNameErrorLabel.setText("Same First Name!");
            firstNameErrorLabel.setVisible(true);
            valid = false;
        }

        // Last Name
        if (lastNameField.getText().trim().isEmpty());
        else if (lastNameField.getText().trim().equals(loggedInOrganizer.getLastName())) {
            lastNameField.getStyleClass().add("error-field");
            lastNameErrorLabel.setText("Same Last Name!");
            lastNameErrorLabel.setVisible(true);
            valid = false;
        }

        // Username
        if (usernameField.getText().trim().isEmpty());
        else if (usernameField.getText().trim().equals(loggedInOrganizer.getUsername())) {
            usernameField.getStyleClass().add("error-field");
            usernameErrorLabel.setText("Same Username!");
            usernameErrorLabel.setVisible(true);
            valid = false;
        }
        else if (usernameField != null) {
            String enteredUsername = usernameField.getText().trim();

            boolean exists = Admin.usernameExists(enteredUsername);

            if (exists) {
                usernameField.getStyleClass().add("error-field");
                usernameErrorLabel.setText("Username already exists!");
                usernameErrorLabel.setVisible(true);
                valid = false;
            }
        }

        // Date of Birth
        if (dobField.getValue() == null);
        else if (dobField.getValue().equals(loggedInOrganizer.getDateOfBirth())) {
            dobField.getStyleClass().add("error-field");
            dobErrorLabel.setText("Same Date of Birth!");
            dobErrorLabel.setVisible(true);
            valid = false;
        }
        else {
            if (dobField.getValue().isAfter(LocalDate.now())) {
                dobField.getStyleClass().add("error-field");
                dobErrorLabel.setText("Date can't be in the future!");
                dobErrorLabel.setVisible(true);
                valid = false;
            } else if (dobField.getValue().plusYears(18).isAfter(LocalDate.now())) {
                dobField.getStyleClass().add("error-field");
                dobErrorLabel.setText("Must be over 18 years old!");
                dobErrorLabel.setVisible(true);
                valid = false;
            }
        }

        // Address
        if (addressField.getText().trim().isEmpty());
        else if (addressField.getText().trim().equals(loggedInOrganizer.getAddress())) {
            addressField.getStyleClass().add("error-field");
            addressErrorLabel.setText("Same address!");
            addressErrorLabel.setVisible(true);
            valid = false;
        }

        // Email
        String email = emailField.getText().trim();
        if (email.isEmpty());
        else if (email.equals(loggedInOrganizer.getEmail())) {
            emailField.getStyleClass().add("error-field");
            emailErrorLabel.setText("Same Email!");
            emailErrorLabel.setVisible(true);
            valid = false;
        }
        else if (!email.contains("@")) {
            emailField.getStyleClass().add("error-field");
            emailErrorLabel.setText("Invalid format!");
            emailErrorLabel.setVisible(true);
            valid = false;
        }

        // Password
        String password = passwordField.getText();
        if (password.trim().isEmpty());
        else if (password.trim().equals(loggedInOrganizer.getPassword())) {
            passwordField.getStyleClass().add("error-field");
            passwordErrorLabel.setText("Same Password!");
            passwordErrorLabel.setVisible(true);
            valid = false;
        }
        else if (password.length() < 8) {
            passwordField.getStyleClass().add("error-field");
            passwordErrorLabel.setText("Password must be over 8 characters!");
            passwordErrorLabel.setVisible(true);
            valid = false;
        }

        // Confirm Password
        if (confirmPasswordField.getText().trim().isEmpty() && password.trim().isEmpty());
        else if (!confirmPasswordField.getText().equals(password)) {
            confirmPasswordField.getStyleClass().add("error-field");
            confirmPasswordErrorLabel.setText("Passwords don't match!");
            confirmPasswordErrorLabel.setVisible(true);
            valid = false;
        }

        return valid;
    }
    private void clearValidationStyles() {
        // TextFields
        TextInputControl[] fields = {
                firstNameField, lastNameField, usernameField, addressField, emailField,
                passwordField, confirmPasswordField
        };

        for (TextInputControl field : fields) {
            field.getStyleClass().removeAll("error-field");
        }

        dobField.getStyleClass().removeAll("error-field");

        // Labels
        firstNameErrorLabel.setVisible(false);
        lastNameErrorLabel.setVisible(false);
        usernameErrorLabel.setVisible(false);
        dobErrorLabel.setVisible(false);
        addressErrorLabel.setVisible(false);
        emailErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);
        confirmPasswordErrorLabel.setVisible(false);
    }
    public void confirmChanges(ActionEvent event) {
        if(!validateFields()) return;

        Database.removeUser(loggedInOrganizer.getUsername(), "Admin");

        // Check and update each field
        if (!firstNameField.getText().trim().isEmpty())
            loggedInOrganizer.setFirstName(firstNameField.getText().trim());

        if (!lastNameField.getText().trim().isEmpty())
            loggedInOrganizer.setLastName(lastNameField.getText().trim());

        if (!usernameField.getText().trim().isEmpty())
            loggedInOrganizer.setUsername(usernameField.getText().trim());

        loggedInOrganizer.setGender(genderComboBox.getValue());

        if (dobField.getValue() != null)
            loggedInOrganizer.setDateOfBirth(dobField.getValue());

        if (!addressField.getText().trim().isEmpty())
            loggedInOrganizer.setAddress(addressField.getText().trim());

        if (!emailField.getText().trim().isEmpty())
            loggedInOrganizer.setEmail(emailField.getText().trim());

        if (!passwordField.getText().isEmpty())
            loggedInOrganizer.setPassword(passwordField.getText());


        Database.organizersDB.add(loggedInOrganizer);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Profile updated successfully!");
        alert.showAndWait();

        welcomeLabel.setText("Welcome, " + loggedInOrganizer.getFirstName() + " " + loggedInOrganizer.getLastName() + " (" + loggedInOrganizer.getUsername() + ") ");
        switchPane(mainMenuPane);
    }

    // dashboard
    public void showDashboard(ActionEvent event) {
        switchPane(showDashboardPane);
    }
    public void availableRoomsButton(ActionEvent event) {
        switchPane(availableRoomsPane);

        allAvailableRooms.setText(Room.availableRooms());
    }
    public void eventsOrganizingButton(ActionEvent event) {
        switchPane(eventsOrganizingPane);
    }
    public void eventsAttendeesButton(ActionEvent event) {
        switchPane(eventsAttendeesPane);
    }

    public void createEvent(ActionEvent event) {
        switchPane(createEventPane);
    }

    public void rentRoom(ActionEvent event) {
        switchPane(rentRoomPane);
    }

    // logout
    public void logout(ActionEvent event) throws IOException {
        Utility.loadScene("/views/LoginView.fxml", event);
    }
}
