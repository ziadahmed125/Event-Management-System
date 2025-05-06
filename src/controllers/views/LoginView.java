package controllers.views;

import actors.*;
import controllers.menus.AdminMenu;
import controllers.menus.AttendeeMenu;
import controllers.menus.OrganizerMenu;
import core.Utility;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginView {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML private Label emailErrorLabel;
    @FXML private Label passwordErrorLabel;
    @FXML private Label loginErrorLabel;

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
    }

    @FXML
    public boolean validateFields() {
        boolean valid = true;

        emailField.getStyleClass().removeAll("error-field");
        passwordField.getStyleClass().removeAll("error-field");

        emailErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);
        loginErrorLabel.setVisible(false);

        // Email
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            emailField.getStyleClass().add("error-field");
            emailErrorLabel.setText("Email is required!");
            emailErrorLabel.setVisible(true);
            valid = false;
        } else if (!email.contains("@")) {
            emailField.getStyleClass().add("error-field");
            emailErrorLabel.setText("Invalid format!");
            emailErrorLabel.setVisible(true);
            valid = false;
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

        // check credentials
        RadioButton selectedRadio = (RadioButton) roleToggleGroup.getSelectedToggle();
        if (selectedRadio != null) {
            String selectedRole = selectedRadio.getText();
            String enteredEmail = emailField.getText().trim();
            String enteredPassword = passwordField.getText().trim();

            User exists = switch (selectedRole) {
                case "Attendee" -> Attendee.getLoggedInUser(enteredEmail, enteredPassword);
                case "Organizer" -> Organizer.getLoggedInUser(enteredEmail, enteredPassword);
                case "Admin" -> Admin.getLoggedInUser(enteredEmail, enteredPassword);
                default -> null;
            };

            if (exists == null) {
                emailField.getStyleClass().add("error-field");
                passwordField.getStyleClass().add("error-field");
                loginErrorLabel.setVisible(true);
                valid = false;
            }
        }

        return valid;
    }

    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        if (!validateFields()) return;

        String email = emailField.getText().trim();
        String password = passwordField.getText();

        RadioButton selectedRadio = (RadioButton) roleToggleGroup.getSelectedToggle();
        if (selectedRadio == null) {
            return;
        }

        String role = selectedRadio.getText();

        switch (role) {
            case "Attendee" -> {
                Attendee attendee = Attendee.getLoggedInUser(email, password);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/menus/attendee_menu.fxml"));
                Parent root = loader.load();
                AttendeeMenu controller = loader.getController();
                controller.setLoggedInAttendee(attendee);

                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
            case "Organizer" -> {
                Organizer organizer = Organizer.getLoggedInUser(email, password);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/menus/organizer_menu.fxml"));
                Parent root = loader.load();
                OrganizerMenu controller = loader.getController();
                controller.setLoggedInOrganizer(organizer);

                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
            case "Admin" -> {
                Admin admin = Admin.getLoggedInUser(email, password);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/menus/admin_menu.fxml"));
                Parent root = loader.load();
                AdminMenu controller = loader.getController();
                controller.setLoggedInAdmin(admin);

                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
        }
    }

    @FXML
    public void switchToSignUp(ActionEvent event) throws IOException {
        Utility.loadScene("/views/SignUpView.fxml", event);
    }
}
