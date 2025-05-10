package controllers.menus;

import actors.Organizer;
import actors.User;
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
    @FXML private VBox eventsOrganizerPane;
    // Manage Event
    @FXML private VBox manageEventPane;
    @FXML private VBox createEventPane;
    @FXML private VBox deleteEventPane;
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
    @FXML private DatePicker eventDateField;
    @FXML private Label eventDateErrorLabel;

    // delete event
    @FXML private ComboBox<Event> deleteEventComboBox;
    @FXML private Label deleteEventErrorLabel;

    // rent room
    @FXML private ComboBox<Room> availableRoomsComboBox;
    @FXML private Label availableRoomsErrorLabel;
    @FXML private Label roomInfoLabel;

    public void setLoggedInOrganizer(Organizer organizer) {
        this.loggedInOrganizer = organizer;

        welcomeLabel.setText("Welcome, " + loggedInOrganizer.getFirstName() + " " + loggedInOrganizer.getLastName() +
                " (" + loggedInOrganizer.getUsername() + ") ");
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
        if (!firstNameField.getText().trim().isEmpty()
                && firstNameField.getText().trim().equals(loggedInOrganizer.getFirstName())) {
            firstNameField.getStyleClass().add("error-field");
            firstNameErrorLabel.setText("Same First Name!");
            firstNameErrorLabel.setVisible(true);
            valid = false;
        }

        // Last Name
        if (!lastNameField.getText().trim().isEmpty()
                && lastNameField.getText().trim().equals(loggedInOrganizer.getLastName())) {
            lastNameField.getStyleClass().add("error-field");
            lastNameErrorLabel.setText("Same Last Name!");
            lastNameErrorLabel.setVisible(true);
            valid = false;
        }

        // Username
        if (!usernameField.getText().trim().isEmpty()
                && usernameField.getText().trim().equals(loggedInOrganizer.getUsername())) {
            usernameField.getStyleClass().add("error-field");
            usernameErrorLabel.setText("Same Username!");
            usernameErrorLabel.setVisible(true);
            valid = false;
        } else if (!usernameField.getText().trim().isEmpty()
                && User.usernameExists(usernameField.getText().trim())) {
            usernameField.getStyleClass().add("error-field");
            usernameErrorLabel.setText("Username already exists!");
            usernameErrorLabel.setVisible(true);
            valid = false;
        }

        // Date of Birth
        if (dobField.getValue() != null
                && dobField.getValue().equals(loggedInOrganizer.getDateOfBirth())) {
            dobField.getStyleClass().add("error-field");
            dobErrorLabel.setText("Same Date of Birth!");
            dobErrorLabel.setVisible(true);
            valid = false;
        }
        else if (dobField.getValue() != null) {
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
        if (!addressField.getText().trim().isEmpty()
                && addressField.getText().trim().equals(loggedInOrganizer.getAddress())) {
            addressField.getStyleClass().add("error-field");
            addressErrorLabel.setText("Same address!");
            addressErrorLabel.setVisible(true);
            valid = false;
        }

        // Email
        String email = emailField.getText().trim();
        if (!email.isEmpty()
                && email.equals(loggedInOrganizer.getEmail())) {
            emailField.getStyleClass().add("error-field");
            emailErrorLabel.setText("Same Email!");
            emailErrorLabel.setVisible(true);
            valid = false;
        } else if (!email.isEmpty()
                && User.emailExists(emailField.getText().trim())) {
            emailField.getStyleClass().add("error-field");
            emailErrorLabel.setText("Email already userExists!");
            emailErrorLabel.setVisible(true);
            valid = false;
        } else if (!email.isEmpty()
                && !email.contains("@")) {
            emailField.getStyleClass().add("error-field");
            emailErrorLabel.setText("Invalid format!");
            emailErrorLabel.setVisible(true);
            valid = false;
        } else {
            // regex to validate email format
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            if (!email.matches(emailRegex)) {
                emailField.getStyleClass().add("error-field");
                emailErrorLabel.setText("Invalid email format!");
                emailErrorLabel.setVisible(true);
                valid = false;
            }
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
        switchPane(eventsOrganizerPane);

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

        allEventAttendees.setText(loggedInOrganizer.getEventOrganizingAttendees(availableEventsComboBox.getValue()));
    }

    // event
    public void manageEvent(ActionEvent event) {
        switchPane(manageEventPane);
    }
    public void createEvent(ActionEvent event) {
        switchPane(createEventPane);

        availableCategoriesComboBox.setItems(FXCollections.observableArrayList(Database.categoriesDB));
        availableOrganizerRoomsComboBox.setItems(FXCollections.observableArrayList(loggedInOrganizer.getRoomsRented()));

        eventNameField.getStyleClass().removeAll("error-field");
        eventDescriptionField.getStyleClass().removeAll("error-field");
        availableCategoriesComboBox.getStyleClass().removeAll("error-field");
        availableRoomsComboBox.getStyleClass().removeAll("error-field");
        eventPriceField.getStyleClass().removeAll("error-field");
        eventNameErrorLabel.setVisible(false);
        eventDescriptionErrorLabel.setVisible(false);
        availableCategoriesErrorLabel.setVisible(false);
        availableRoomsErrorLabel.setVisible(false);
        eventPriceErrorLabel.setVisible(false);

        eventDateField.getStyleClass().removeAll("error-field");
        eventDateErrorLabel.setVisible(false);
    }

    public void confirmAddEventButton(ActionEvent event) {
        // Clear all previous errors
        eventNameField.getStyleClass().removeAll("error-field");
        eventDescriptionField.getStyleClass().removeAll("error-field");
        availableCategoriesComboBox.getStyleClass().removeAll("error-field");
        availableOrganizerRoomsComboBox.getStyleClass().removeAll("error-field");
        eventPriceField.getStyleClass().removeAll("error-field");
        eventDateField.getStyleClass().removeAll("error-field");

        eventNameErrorLabel.setVisible(false);
        eventDescriptionErrorLabel.setVisible(false);
        availableCategoriesErrorLabel.setVisible(false);
        availableRoomsOrganizingErrorLabel.setVisible(false);
        eventPriceErrorLabel.setVisible(false);
        eventDateErrorLabel.setVisible(false);

        boolean valid = true;

        // Validate event date
        if (eventDateField.getValue() == null) {
            eventDateField.getStyleClass().add("error-field");
            eventDateErrorLabel.setText("Date of Event is required!");
            eventDateErrorLabel.setVisible(true);
            valid = false;
        } else if (eventDateField.getValue().isBefore(LocalDate.now())) {
            eventDateField.getStyleClass().add("error-field");
            eventDateErrorLabel.setText("Date can't be in the Past!");
            eventDateErrorLabel.setVisible(true);
            valid = false;
        }

        // Validate other fields
        if(eventNameField.getText().trim().isEmpty()) {
            eventNameField.getStyleClass().add("error-field");
            eventNameErrorLabel.setText("Can't be empty!");
            eventNameErrorLabel.setVisible(true);
            valid = false;
        }

        if(eventDescriptionField.getText().trim().isEmpty()) {
            eventDescriptionField.getStyleClass().add("error-field");
            eventDescriptionErrorLabel.setText("Can't be empty!");
            eventDescriptionErrorLabel.setVisible(true);
            valid = false;
        }

        if(availableCategoriesComboBox.getValue() == null) {  // Changed from checking comboBox to its value
            availableCategoriesComboBox.getStyleClass().add("error-field");
            availableCategoriesErrorLabel.setText("Can't be empty!");
            availableCategoriesErrorLabel.setVisible(true);
            valid = false;
        }

        if(availableOrganizerRoomsComboBox.getValue() == null) {  // Changed from checking comboBox to its value
            availableOrganizerRoomsComboBox.getStyleClass().add("error-field");
            availableRoomsOrganizingErrorLabel.setText("Can't be empty!");
            availableRoomsOrganizingErrorLabel.setVisible(true);
            valid = false;
        }

        if(eventPriceField.getText().trim().isEmpty()) {
            eventPriceField.getStyleClass().add("error-field");
            eventPriceErrorLabel.setText("Can't be empty!");
            eventPriceErrorLabel.setVisible(true);
            valid = false;
        } else {
            try {
                double value = Double.parseDouble(eventPriceField.getText().trim());
                if (value <= 0) {
                    eventPriceField.getStyleClass().add("error-field");
                    eventPriceErrorLabel.setText("Price must be positive!");
                    eventPriceErrorLabel.setVisible(true);
                    valid = false;
                }
            } catch (NumberFormatException e) {
                eventPriceField.getStyleClass().add("error-field");
                eventPriceErrorLabel.setText("Must be a valid number!");
                eventPriceErrorLabel.setVisible(true);
                valid = false;
            }
        }

        if(!valid) return;

        // Create the event if all validations pass
        Event e = new Event();
        e.setName(eventNameField.getText().trim());
        e.setDescription(eventDescriptionField.getText().trim());
        e.setCategory(availableCategoriesComboBox.getValue());
        e.setRoom(availableOrganizerRoomsComboBox.getValue());
        e.setOrganizer(loggedInOrganizer);
        e.setTicketPrice(Double.parseDouble(eventPriceField.getText().trim()));
        e.setDateTime(eventDateField.getValue());

        e.create();
        loggedInOrganizer.setEventsOrganizing(e);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Event created successfully!");
        alert.showAndWait();

        // Clear fields
        eventNameField.setText("");
        eventDescriptionField.setText("");
        eventPriceField.setText("");
        eventDateField.setValue(null);
        availableCategoriesComboBox.setValue(null);
        availableOrganizerRoomsComboBox.setValue(null);

        switchPane(mainMenuPane);
    }

    public void deleteEvent(ActionEvent event) {
        switchPane(deleteEventPane);

        deleteEventComboBox.setItems(FXCollections.observableArrayList(loggedInOrganizer.getEventsOrganizing()));

        deleteEventComboBox.getStyleClass().removeAll("error-field");
        deleteEventErrorLabel.setVisible(false);
    }
    public void confirmDeleteEventButton(ActionEvent event) {
        deleteEventComboBox.getStyleClass().removeAll("error-field");
        deleteEventErrorLabel.setVisible(false);

        if(deleteEventComboBox.getValue() == null) {
            deleteEventComboBox.getStyleClass().add("error-field");
            deleteEventErrorLabel.setText("Must select a category!");
            deleteEventErrorLabel.setVisible(true);

            return;
        }

        deleteEventComboBox.getValue().delete();
        loggedInOrganizer.deleteEvent(deleteEventComboBox.getValue());

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Event Deleted Successfully!");
        alert.showAndWait();

        switchPane(manageEventPane);
    }

    // rent room
    public void rentRoom(ActionEvent event) {
        switchPane(rentRoomPane);

        availableRoomsComboBox.setItems(FXCollections.observableArrayList(Room.getAvailableRooms()));

        availableRoomsComboBox.getStyleClass().removeAll("error-field");
        availableRoomsErrorLabel.setVisible(false);
        roomInfoLabel.setVisible(false);
    }
    public void confirmRoomButton(ActionEvent event) {
        availableRoomsComboBox.getStyleClass().removeAll("error-field");
        availableRoomsErrorLabel.setVisible(false);

        if(availableRoomsComboBox.getValue() == null) {
            availableRoomsComboBox.getStyleClass().add("error-field");
            availableRoomsErrorLabel.setText("Must select a room!");
            availableRoomsErrorLabel.setVisible(true);

            return;
        }

        roomInfoLabel.getStyleClass().removeAll("error-label");
        roomInfoLabel.setText(availableRoomsComboBox.getValue().getRoomInfo());
        roomInfoLabel.setVisible(true);
    }
    public void rentRoomButton(ActionEvent event) {
        roomInfoLabel.getStyleClass().removeAll("error-label");

        if(availableRoomsComboBox.getValue() == null) {
            availableRoomsComboBox.getStyleClass().add("error-field");
            availableRoomsErrorLabel.setText("Must select a room!");
            availableRoomsErrorLabel.setVisible(true);

            return;
        }

        Room room = availableRoomsComboBox.getValue();

        if(room.getPrice() > loggedInOrganizer.getBalance()) {
            roomInfoLabel.getStyleClass().add("error-label");
            roomInfoLabel.setText("Not enough funds!");
            roomInfoLabel.setVisible(true);

            return;
        }

        loggedInOrganizer.deductFunds(room.getPrice());
        loggedInOrganizer.setRoomsRented(room);
        Room.updateRoomAvailability(room.getRoomId(), false);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Room rented successfully!");
        alert.showAndWait();

        availableRoomsComboBox.setValue(null);

        roomInfoLabel.setVisible(false);

        switchPane(mainMenuPane);
    }

    // logout
    public void logout(ActionEvent event) throws IOException {
        Utility.loadScene("/views/LoginView.fxml", event);
    }
}
