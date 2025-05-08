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
import models.Category;
import models.Event;
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
    // Wallet
    @FXML private VBox walletPane;
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

    // wallet
    @FXML private Label userBalanceLabel;
    @FXML private TextField addBalanceField;
    @FXML private Label addBalanceErrorLabel;

    // dashboard
    @FXML private Label allAvailableRooms;
    @FXML private Label allEventsOrganizing;
    @FXML private Label allEventAttendees;
    @FXML private ComboBox<String> availableEventsComboBox;
    @FXML private Label availableEventsErrorLabel;

    // create event
    @FXML private TextField eventNameField;
    @FXML private Label eventNameErrorLabel;
    @FXML private TextField eventDescriptionField;
    @FXML private Label eventDescriptionErrorLabel;
    @FXML private ComboBox<Category> availableCategoriesComboBox;
    @FXML private Label availableCategoriesErrorLabel;
    @FXML private ComboBox<Room> availableOrganizerRoomsComboBox;
    @FXML private Label availableRoomsOrganizingErrorLabel;
    @FXML private TextField eventPriceField;
    @FXML private Label eventPriceErrorLabel;

    // rent room
    @FXML private ComboBox<Room> availableRoomsComboBox;
    @FXML private Label availableRoomsErrorLabel;
    @FXML private Label roomInfoLabel;

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

    // wallet
    public void walletButton(ActionEvent event) {
        switchPane(walletPane);

        userBalanceLabel.setText(loggedInOrganizer.getBalanceString());

        // clearing validation styles
        addBalanceField.getStyleClass().removeAll("error-field");
        addBalanceErrorLabel.setVisible(false);

        // clearing field
        addBalanceField.setText("");
    }
    public void AddBalanceButton(ActionEvent event) {
        // clearing validation styles
        addBalanceField.getStyleClass().removeAll("error-field");
        addBalanceErrorLabel.setVisible(false);

        if (addBalanceField.getText().trim().isEmpty()) {
            addBalanceField.getStyleClass().add("error-field");
            addBalanceErrorLabel.setText("Must enter a value!");
            addBalanceErrorLabel.setVisible(true);

            return;
        }

        try {
            double value = Double.parseDouble(addBalanceField.getText().trim());
        } catch (NumberFormatException e) {
            addBalanceField.getStyleClass().add("error-field");
            addBalanceErrorLabel.setText("Must be a number!");
            addBalanceErrorLabel.setVisible(true);

            return;
        }

        loggedInOrganizer.addFunds(Double.parseDouble(addBalanceField.getText().trim()));
        addBalanceField.setText("");
        userBalanceLabel.setText(loggedInOrganizer.getBalanceString());
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

        allEventsOrganizing.setText(loggedInOrganizer.getEventsOrganizingString());
    }
    public void eventsAttendeesButton(ActionEvent event) {
        switchPane(eventsAttendeesPane);

        availableEventsComboBox.setItems(FXCollections.observableArrayList(loggedInOrganizer.getEventsOrganizingNames()));

        availableEventsComboBox.getStyleClass().removeAll("error-field");
        availableEventsErrorLabel.setVisible(false);
    }
    public void confirmEventButton(ActionEvent event) {
        availableEventsComboBox.getStyleClass().removeAll("error-field");
        availableEventsErrorLabel.setVisible(false);

        if(availableEventsComboBox.getValue() == null || availableEventsComboBox.getValue().isEmpty()) {
            availableEventsComboBox.getStyleClass().add("error-field");
            availableEventsErrorLabel.setText("Must Select an Event");
            availableEventsErrorLabel.setVisible(true);
            return;
        }

        allEventAttendees.setText(loggedInOrganizer.getEventsOrganizingAttendees(availableEventsComboBox.getValue()));
    }

    // event
    public void createEvent(ActionEvent event) {
        switchPane(createEventPane);

        availableCategoriesComboBox.setItems(FXCollections.observableArrayList(Database.categoriesDB));
        availableOrganizerRoomsComboBox.setItems(FXCollections.observableArrayList(loggedInOrganizer.getRoomsRented()));
    }
    public void confirmAddEventButton(ActionEvent event) {
        // validate

        Event e = new Event();
        e.setName(eventNameField.getText().trim());
        e.setDescription(eventDescriptionField.getText().trim());
        e.setCategory(availableCategoriesComboBox.getValue());
        e.setRoom(availableOrganizerRoomsComboBox.getValue());
        e.setOrganizer(loggedInOrganizer);
        e.setTicketPrice(Double.parseDouble(eventPriceField.getText().trim()));

        Event.create(e);
    }

    // rent room
    public void rentRoom(ActionEvent event) {
        switchPane(rentRoomPane);

        availableRoomsComboBox.setItems(FXCollections.observableArrayList(Room.getAvailableRooms()));
    }
    public void confirmRoomButton(ActionEvent event) {
        // validate

        roomInfoLabel.setText(availableRoomsComboBox.getValue().getRoomInfo());
    }
    public void rentRoomButton(ActionEvent event) {
        // validate balance

        Room room = availableRoomsComboBox.getValue();

        // validate funds

        loggedInOrganizer.deductFunds(room.getPrice());
        loggedInOrganizer.setRoomsRented(room);
        Room.updateRoomAvailability(room.getRoomId(), false);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Room rented successfully!");
        alert.showAndWait();

        switchPane(mainMenuPane);
    }

    // logout
    public void logout(ActionEvent event) throws IOException {
        Utility.loadScene("/views/LoginView.fxml", event);
    }
}
