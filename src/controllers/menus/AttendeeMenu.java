package controllers.menus;

import actors.Admin;
import actors.Attendee;
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
import models.Event;
import models.Gender;

import java.io.IOException;
import java.time.LocalDate;

public class AttendeeMenu {
    private Attendee loggedInAttendee;

    // Panes
    @FXML private StackPane rootPane;
    // Main Menu
    @FXML private VBox mainMenuPane;
    // Profile
    @FXML private VBox profilePane;
    @FXML private VBox InterestPane;
    @FXML private TextField addInterestField;
    @FXML private VBox editProfilePane;
    // Wallet
    @FXML private VBox walletPane;
    // Dashboard
    @FXML private VBox dashboardPane;
    @FXML private VBox eventsPane;
    @FXML private VBox buyTicketPane;

    // Main Menu
    @FXML private Label welcomeLabel;

    // profile
    @FXML private Label profileInfo;

    //Add Interest
    @FXML private Label addInterestErrorLabel;

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
    @FXML private Label allEvents;
    @FXML private TextField eventIdField;
    @FXML private Label eventIdErrorLabel;
    @FXML private Label eventPriceLabel;
    @FXML private Label buyTicketErrorLabel;

    public void setLoggedInAttendee(Attendee attendee) {
        this.loggedInAttendee = attendee;

        welcomeLabel.setText("Welcome, " + attendee.getFirstName() + " " + attendee.getLastName() + " (" + attendee.getUsername() + ") ");
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
        profileInfo.setText(loggedInAttendee.toString());
    }

    // add interest
    public void addInterest (ActionEvent event) {
        switchPane(InterestPane);

        // clearing validation styles
        addInterestField.getStyleClass().removeAll("error-field");
        addInterestErrorLabel.setVisible(false);

        // clearing field
        addInterestField.setText("");
    }
    public void addInterestButton(ActionEvent event){
        // clearing validation styles
        addInterestField.getStyleClass().removeAll("error-field");
        addInterestErrorLabel.setVisible(false);

        if (addInterestField.getText().trim().isEmpty()) {
            addInterestField.getStyleClass().add("error-field");
            addInterestErrorLabel.setText("Can't be Empty!");
            addInterestErrorLabel.setVisible(true);
            return;
        }

        if (!loggedInAttendee.isInterestUnique(addInterestField.getText().trim())) {
            addInterestField.getStyleClass().add("error-field");
            addInterestErrorLabel.setText("Interest already Exists!");
            addInterestErrorLabel.setVisible(true);
            return;
        }

        if(loggedInAttendee.numOfInterests() == 4) {
            addInterestField.getStyleClass().add("error-field");
            addInterestErrorLabel.setText("Can't have more than 4 interests!");
            addInterestErrorLabel.setVisible(true);
            return;
        }

        loggedInAttendee.setInterests(addInterestField.getText().trim());

        addInterestField.setText("");
    }

    // edit profile
    public void editProfileButton(ActionEvent event) {
        switchPane(editProfilePane);

        firstNameField.setPromptText(loggedInAttendee.getFirstName());
        lastNameField.setPromptText(loggedInAttendee.getLastName());
        usernameField.setPromptText(loggedInAttendee.getUsername());
        genderComboBox.setValue(loggedInAttendee.getGender());
        dobField.setValue(loggedInAttendee.getDateOfBirth());
        addressField.setPromptText(loggedInAttendee.getAddress());
        emailField.setPromptText(loggedInAttendee.getEmail());
        passwordField.setPromptText("New Password");
        confirmPasswordField.setPromptText("Confirm New Password");
    }
    public boolean validateFields() {
        boolean valid = true;

        // Reset styles and labels
        clearValidationStyles();

        // First Name
        if (firstNameField.getText().trim().isEmpty());
        else if (firstNameField.getText().trim().equals(loggedInAttendee.getFirstName())) {
            firstNameField.getStyleClass().add("error-field");
            firstNameErrorLabel.setText("Same First Name!");
            firstNameErrorLabel.setVisible(true);
            valid = false;
        }

        // Last Name
        if (lastNameField.getText().trim().isEmpty());
        else if (lastNameField.getText().trim().equals(loggedInAttendee.getLastName())) {
            lastNameField.getStyleClass().add("error-field");
            lastNameErrorLabel.setText("Same Last Name!");
            lastNameErrorLabel.setVisible(true);
            valid = false;
        }

        // Username
        if (usernameField.getText().trim().isEmpty());
        else if (usernameField.getText().trim().equals(loggedInAttendee.getUsername())) {
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
        else if (dobField.getValue().equals(loggedInAttendee.getDateOfBirth())) {
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
        else if (addressField.getText().trim().equals(loggedInAttendee.getAddress())) {
            addressField.getStyleClass().add("error-field");
            addressErrorLabel.setText("Same address!");
            addressErrorLabel.setVisible(true);
            valid = false;
        }

        // Email
        String email = emailField.getText().trim();
        if (email.isEmpty());
        else if (email.equals(loggedInAttendee.getEmail())) {
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
        else if (password.trim().equals(loggedInAttendee.getPassword())) {
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

        Database.removeUser(loggedInAttendee.getUsername(), "Admin");

        // Check and update each field
        if (!firstNameField.getText().trim().isEmpty())
            loggedInAttendee.setFirstName(firstNameField.getText().trim());

        if (!lastNameField.getText().trim().isEmpty())
            loggedInAttendee.setLastName(lastNameField.getText().trim());

        if (!usernameField.getText().trim().isEmpty())
            loggedInAttendee.setUsername(usernameField.getText().trim());

        loggedInAttendee.setGender(genderComboBox.getValue());

        if (dobField.getValue() != null)
            loggedInAttendee.setDateOfBirth(dobField.getValue());

        if (!addressField.getText().trim().isEmpty())
            loggedInAttendee.setAddress(addressField.getText().trim());

        if (!emailField.getText().trim().isEmpty())
            loggedInAttendee.setEmail(emailField.getText().trim());

        if (!passwordField.getText().isEmpty())
            loggedInAttendee.setPassword(passwordField.getText());


        Database.attendeesDB.add(loggedInAttendee);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Profile updated successfully!");
        alert.showAndWait();

        welcomeLabel.setText("Welcome, " + loggedInAttendee.getFirstName() + " " + loggedInAttendee.getLastName() + " (" + loggedInAttendee.getUsername() + ") ");
        switchPane(mainMenuPane);
    }

    // wallet
    public void walletButton(ActionEvent event) {
        switchPane(walletPane);

        userBalanceLabel.setText(loggedInAttendee.getBalanceString());

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

        loggedInAttendee.addFunds(Double.parseDouble(addBalanceField.getText().trim()));
        addBalanceField.setText("");
        userBalanceLabel.setText(loggedInAttendee.getBalanceString());
    }

    // dashboard
    public void showDashboard(ActionEvent event) {
        switchPane(dashboardPane);
    }
    // show events
    public void eventsButton(ActionEvent event) {
        switchPane(eventsPane);
        allEvents.setText(Event.showEvents());
    }
    // buy ticket
    public void buyTicketsButton(ActionEvent event) {
        switchPane(buyTicketPane);

        // clearing validation styles
        eventIdField.getStyleClass().removeAll("error-field");
        eventIdErrorLabel.setVisible(false);
        buyTicketErrorLabel.setVisible(false);

        // clearing field
        eventIdField.setText("");
    }
    public void eventIdButton(ActionEvent event) {
        // clearing validation styles
        eventIdField.getStyleClass().removeAll("error-field");
        eventIdErrorLabel.setVisible(false);
        buyTicketErrorLabel.setVisible(false);

        if(eventIdField.getText().trim().isEmpty()) {
            eventIdField.getStyleClass().add("error-field");
            eventIdErrorLabel.setText("Can't be empty!");
            eventIdErrorLabel.setVisible(true);

            return;
        }

        if(Event.getEvent(eventIdField.getText().trim()) == null) {
            eventIdField.getStyleClass().add("error-field");
            eventIdErrorLabel.setText("Event doesn't exist!");
            eventIdErrorLabel.setVisible(true);

            return;
        }

        Event e = Event.getEvent(eventIdField.getText().trim());

        eventPriceLabel.setText("Price = $" + e.getTicketPrice());
    }
    public void buyTicketButton(ActionEvent event) {
        // clearing validation styles
        eventIdField.getStyleClass().removeAll("error-field");
        eventIdErrorLabel.setVisible(false);
        buyTicketErrorLabel.setVisible(false);

        if(eventIdField.getText().trim().isEmpty()) {
            eventIdField.getStyleClass().add("error-field");
            eventIdErrorLabel.setText("Can't be empty!");
            eventIdErrorLabel.setVisible(true);

            return;
        }

        if(Event.getEvent(eventIdField.getText().trim()) == null) {
            eventIdField.getStyleClass().add("error-field");
            eventIdErrorLabel.setText("Event doesn't exist!");
            eventIdErrorLabel.setVisible(true);

            return;
        }

        Event e = Event.getEvent(eventIdField.getText().trim());

        if(e.getTicketPrice() > loggedInAttendee.getBalance()) {
            buyTicketErrorLabel.setText("Not enough funds!");
            buyTicketErrorLabel.setVisible(true);

            return;
        }

        loggedInAttendee.deductFunds(e.getTicketPrice());
        e.addAttendee(loggedInAttendee);
        loggedInAttendee.setTickets(e.getEventId());

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ticket bought successfully!");
        alert.showAndWait();

        switchPane(mainMenuPane);
    }

    // logout
    public void logout(ActionEvent event) throws IOException {
        Utility.loadScene("/views/LoginView.fxml", event);
    }
}
