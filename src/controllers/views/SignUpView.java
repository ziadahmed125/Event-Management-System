package controllers.views;

import actors.*;

import core.Database;
import core.Utility;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Gender;

import java.io.IOException;
import java.time.LocalDate;

public class SignUpView {

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
    @FXML private Label genderErrorLabel;
    @FXML private Label dobErrorLabel;
    @FXML private Label addressErrorLabel;
    @FXML private Label emailErrorLabel;
    @FXML private Label passwordErrorLabel;
    @FXML private Label confirmPasswordErrorLabel;

    @FXML private RadioButton attendeeRadio;
    @FXML private RadioButton organizerRadio;
    @FXML private RadioButton adminRadio;

    private ToggleGroup roleToggleGroup;

    @FXML
    public void initialize() {
        roleToggleGroup = new ToggleGroup();
        attendeeRadio.setToggleGroup(roleToggleGroup);
        organizerRadio.setToggleGroup(roleToggleGroup);
        adminRadio.setToggleGroup(roleToggleGroup);

        // default selection
        attendeeRadio.setSelected(true);
        genderComboBox.setItems(FXCollections.observableArrayList(Gender.values()));
        genderComboBox.setValue(Gender.Male);
    }

    @FXML
    public boolean validateFields() {
        boolean valid = true;

        // Reset styles and labels
        clearValidationStyles();

        // First Name
        if (firstNameField.getText().trim().isEmpty()) {
            firstNameField.getStyleClass().add("error-field");
            firstNameErrorLabel.setVisible(true);
            valid = false;
        }

        // Last Name
        if (lastNameField.getText().trim().isEmpty()) {
            lastNameField.getStyleClass().add("error-field");
            lastNameErrorLabel.setVisible(true);
            valid = false;
        }

        // Username
        if (usernameField.getText().trim().isEmpty()) {
            usernameField.getStyleClass().add("error-field");
            usernameErrorLabel.getStyleClass().add("error-label");
            usernameErrorLabel.setText("Username is required!");
            usernameErrorLabel.setVisible(true);
            valid = false;
        } else if (User.usernameExists(usernameField.getText().trim())) {
            usernameField.getStyleClass().add("error-field");
            usernameErrorLabel.setText("Username already userExists!");
            usernameErrorLabel.setVisible(true);
            valid = false;
        }

        // Date of Birth
        if (dobField.getValue() == null) {
            dobField.getStyleClass().add("error-field");
            dobErrorLabel.setText("Date of Birth is required!");
            dobErrorLabel.setVisible(true);
            valid = false;
        } else {
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
        if (addressField.getText().trim().isEmpty()) {
            addressField.getStyleClass().add("error-field");
            addressErrorLabel.setVisible(true);
            valid = false;
        }

        // Email
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            emailField.getStyleClass().add("error-field");
            emailErrorLabel.setText("Email is required!");
            emailErrorLabel.setVisible(true);
            valid = false;
        } else if (User.emailExists(email)) {
            emailField.getStyleClass().add("error-field");
            emailErrorLabel.setText("Email already exists!");
            emailErrorLabel.setVisible(true);
            valid = false;
        } else if (!email.contains("@")) {
            emailField.getStyleClass().add("error-field");
            emailErrorLabel.setText("Invalid format! '@' symbol missing.");
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
        if (password.trim().isEmpty()) {
            passwordField.getStyleClass().add("error-field");
            passwordErrorLabel.setText("Password is required!");
            passwordErrorLabel.setVisible(true);
            valid = false;
        } else if (password.length() < 8) {
            passwordField.getStyleClass().add("error-field");
            passwordErrorLabel.setText("Password must be over 8 characters!");
            passwordErrorLabel.setVisible(true);
            valid = false;
        }

        // Confirm Password
        if (confirmPasswordField.getText().trim().isEmpty()) {
            confirmPasswordField.getStyleClass().add("error-field");
            confirmPasswordErrorLabel.setText("Confirm password!");
            confirmPasswordErrorLabel.setVisible(true);
            valid = false;
        } else if (!confirmPasswordField.getText().equals(password)) {
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

    @FXML
    public void handleSignUp(ActionEvent event) throws IOException {
        if (!validateFields()) return;

        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String address = addressField.getText().trim();
        Gender gender = genderComboBox.getValue();
        LocalDate dob = dobField.getValue();

        RadioButton selectedRadio = (RadioButton) roleToggleGroup.getSelectedToggle();
        if (selectedRadio == null) {
            return;
        }

        String role = selectedRadio.getText();

        switch (role) {
            case "Attendee" -> {
                Attendee attendee = new Attendee(firstName, lastName, username, gender, dob, address, email, password);
                Database.attendeesDB.add(attendee);
            }
            case "Organizer" -> {
                Organizer organizer = new Organizer(firstName, lastName, username, gender, dob, address, email, password);
                Database.organizersDB.add(organizer);
            }
            case "Admin" -> {
                Admin admin = new Admin(firstName, lastName, username, gender, dob, address, email, password);
                Database.adminsDB.add(admin);
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Profile created successfully!");
        alert.showAndWait();

        switchToLogin(event); // redirect to log in after sign up
    }

    @FXML
    public void switchToLogin(ActionEvent event) throws IOException {
        Utility.loadScene("/views/LoginView.fxml", event);
    }
}
