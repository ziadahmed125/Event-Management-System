package controllers.menus;

import actors.Admin;
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

public class AdminMenu {
    private Admin loggedInAdmin;

    // Panes
    @FXML private StackPane rootPane;
    // Main Menu
    @FXML private VBox setRolePane;
    @FXML private VBox setWorkingHoursPane;
    @FXML private VBox mainMenuPane;
    // Profile
    @FXML private VBox profilePane;
    @FXML private VBox editProfilePane;
    // Dashboard
    @FXML private VBox showDashboardPane;
    @FXML private VBox eventsPane;
    @FXML private VBox organizersPane;
    @FXML private VBox attendeesPane;
    // Manage Room
    @FXML private VBox manageRoomsPane;
    @FXML private VBox showRoomsPane;
    @FXML private VBox addRoomPane;
    @FXML private VBox updateRoomAvailabilityPane;
    // Manage Categories
    @FXML private VBox manageCategoriesPane;
    @FXML private VBox showCategoriesPane;
    @FXML private VBox addCategoryPane;
    @FXML private VBox editCategoryPane;
    @FXML private VBox deleteCategoryPane;


    // Role and Working Hours
    @FXML private TextField setRoleField;
    @FXML private Label setRoleErrorLabel;
    @FXML private TextField setWorkingHoursField;
    @FXML private Label setWorkingHoursLabel;

    // Main Menu
    @FXML private Label welcomeLabel;

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
    @FXML private Label allEvents;
    @FXML private Label allOrganizers;
    @FXML private Label allAttendees;

    // manage rooms
    @FXML private Label allRooms;
    @FXML private Label roomTypeErrorLabel;
    @FXML private Label roomPriceErrorLabel;
    @FXML private Label availabilityComboBoxErrorLabel;
    @FXML private Label roomIdErrorLabel;
    @FXML private Label updateAvailabilityComboBoxErrorLabel;
    @FXML private TextField roomTypeField;
    @FXML private TextField roomPriceField;
    @FXML private TextField roomIdField;
    @FXML private ComboBox availabilityComboBox;
    @FXML private ComboBox updateAvailabilityComboBox;

    // manage categories
    @FXML private Label allCategories;
    @FXML private TextField categoryNameField;
    @FXML private TextField categoryDescriptionField;
    @FXML private Label categoryNameErrorLabel;
    @FXML private Label categoryDescriptionErrorLabel;
    @FXML private TextField editCategoryNameField;
    @FXML private TextField editCategoryDescriptionField;
    @FXML private Label editCategoryNameErrorLabel;
    @FXML private Label editCategoryDescriptionErrorLabel;
    @FXML private ComboBox<String> availableCategoryComboBox;
    @FXML private ComboBox<String> editAvailableCategoryComboBox;
    @FXML private Label availableCategoryErrorLabel;
    @FXML private Label editAvailableCategoryErrorLabel;

    public void setLoggedInAdmin(Admin admin) {
        this.loggedInAdmin = admin;
        welcomeLabel.setText("Welcome, " + admin.getFirstName() + " " + admin.getLastName() + " (" + admin.getUsername() + ") ");
        switchPane(mainMenuPane);

        if (admin.getRole().equals("null")) {
            switchPane(setRolePane);
        } else switchPane(mainMenuPane);
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
        availableCategoryComboBox.setItems(FXCollections.observableArrayList(Category.getCategoryNames()));
        editAvailableCategoryComboBox.setItems(FXCollections.observableArrayList(Category.getCategoryNames()));
    }

    public void confirmRole(ActionEvent event) {
        setRoleField.getStyleClass().removeAll("error-field");
        setRoleErrorLabel.setVisible(false);

        if(setRoleField.getText().trim().isEmpty()) {
            setRoleField.getStyleClass().add("error-field");
            setRoleErrorLabel.setText("Filed can't be empty!");
            setRoleField.setVisible(true);

            return;
        }

        loggedInAdmin.setRole(setRoleField.getText().trim());

        if (loggedInAdmin.getWorkingHours() == 0){
            switchPane(setWorkingHoursPane);
        } else switchPane(mainMenuPane);
    }

    public void confirmWorkingHours(ActionEvent event) {
        setWorkingHoursField.getStyleClass().removeAll("error-field");
        setWorkingHoursLabel.setVisible(false);

        if(setWorkingHoursField.getText().trim().isEmpty()) {
            setWorkingHoursField.getStyleClass().add("error-field");
            setWorkingHoursLabel.setText("Filed can't be empty!");
            setRoleField.setVisible(true);

            return;
        }

        try {
            Integer.parseInt(setWorkingHoursField.getText().trim());

        } catch (NumberFormatException e) {
            setWorkingHoursField.getStyleClass().add("error-field");
            setWorkingHoursLabel.setText("Must be a number!");
            setRoleField.setVisible(true);

            return;
        }

        loggedInAdmin.setWorkingHours(Integer.parseInt(setWorkingHoursField.getText().trim()));

        switchPane(mainMenuPane);
    }

    public void back(ActionEvent event) {
        switchPane(mainMenuPane);
    }

    // profile
    public void viewProfile(ActionEvent event) {
        switchPane(profilePane);
        profileInfo.setText(loggedInAdmin.toString());
    }

    // edit profile
    public void editProfile(ActionEvent event) {
        switchPane(editProfilePane);

        firstNameField.setPromptText(loggedInAdmin.getFirstName());
        lastNameField.setPromptText(loggedInAdmin.getLastName());
        usernameField.setPromptText(loggedInAdmin.getUsername());
        genderComboBox.setValue(loggedInAdmin.getGender());
        dobField.setValue(loggedInAdmin.getDateOfBirth());
        addressField.setPromptText(loggedInAdmin.getAddress());
        emailField.setPromptText(loggedInAdmin.getEmail());
        passwordField.setPromptText("New Password");
        confirmPasswordField.setPromptText("Confirm New Password");
    }
    public boolean validateFields() {
        boolean valid = true;

        // Reset styles and labels
        clearValidationStyles();

        // First Name
        if (firstNameField.getText().trim().isEmpty());
        else if (firstNameField.getText().trim().equals(loggedInAdmin.getFirstName())) {
            firstNameField.getStyleClass().add("error-field");
            firstNameErrorLabel.setText("Same First Name!");
            firstNameErrorLabel.setVisible(true);
            valid = false;
        }

        // Last Name
        if (lastNameField.getText().trim().isEmpty());
        else if (lastNameField.getText().trim().equals(loggedInAdmin.getLastName())) {
            lastNameField.getStyleClass().add("error-field");
            lastNameErrorLabel.setText("Same Last Name!");
            lastNameErrorLabel.setVisible(true);
            valid = false;
        }

        // Username
        if (usernameField.getText().trim().isEmpty());
        else if (usernameField.getText().trim().equals(loggedInAdmin.getUsername())) {
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
        else if (dobField.getValue().equals(loggedInAdmin.getDateOfBirth())) {
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
        else if (addressField.getText().trim().equals(loggedInAdmin.getAddress())) {
            addressField.getStyleClass().add("error-field");
            addressErrorLabel.setText("Same address!");
            addressErrorLabel.setVisible(true);
            valid = false;
        }

        // Email
        String email = emailField.getText().trim();
        if (email.isEmpty());
        else if (email.equals(loggedInAdmin.getEmail())) {
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
        else if (password.trim().equals(loggedInAdmin.getPassword())) {
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

        Database.removeUser(loggedInAdmin.getUsername(), "Admin");

        // Check and update each field
        if (!firstNameField.getText().trim().isEmpty())
            loggedInAdmin.setFirstName(firstNameField.getText().trim());

        if (!lastNameField.getText().trim().isEmpty())
            loggedInAdmin.setLastName(lastNameField.getText().trim());

        if (!usernameField.getText().trim().isEmpty())
            loggedInAdmin.setUsername(usernameField.getText().trim());

        loggedInAdmin.setGender(Gender.valueOf(genderComboBox.getValue().toString()));

        if (dobField.getValue() != null)
            loggedInAdmin.setDateOfBirth(dobField.getValue());

        if (!addressField.getText().trim().isEmpty())
            loggedInAdmin.setAddress(addressField.getText().trim());

        if (!emailField.getText().trim().isEmpty())
            loggedInAdmin.setEmail(emailField.getText().trim());

        if (!passwordField.getText().isEmpty())
            loggedInAdmin.setPassword(passwordField.getText());


        Database.adminsDB.add(loggedInAdmin);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Profile updated successfully!");
        alert.showAndWait();

        welcomeLabel.setText("Welcome, " + loggedInAdmin.getFirstName() + " " + loggedInAdmin.getLastName() + " (" + loggedInAdmin.getUsername() + ") ");
        switchPane(mainMenuPane);
    }

    // dashboard
    public void showDashboard(ActionEvent event) {
        switchPane(showDashboardPane);
    }
    public void eventsButton(ActionEvent event) {
        switchPane(eventsPane);
        allEvents.setText(Event.showEvents());
    }
    public void organizersButton(ActionEvent event) {
        switchPane(organizersPane);
        allOrganizers.setText(loggedInAdmin.showOrganizers());
    }
    public void attendeesButton(ActionEvent event) {
        switchPane(attendeesPane);
        allAttendees.setText(loggedInAdmin.showAttendees());
    }

    // manage rooms
    public void manageRooms(ActionEvent event) {
        switchPane(manageRoomsPane);
    }
    public void showRoomsButton(ActionEvent event) {
        switchPane(showRoomsPane);
        allRooms.setText(loggedInAdmin.showRooms());
    }
    public void addRoomButton(ActionEvent event) {
        switchPane(addRoomPane);
    }
    public void confirmAddRoomButton(ActionEvent event) {
        if(!validateAddRoom()) return;

        boolean availability = availabilityComboBox.getValue().toString().equalsIgnoreCase("Yes");
        Double price = Double.parseDouble(roomPriceField.getText());

        Room room = new Room(availability, roomTypeField.getText(), price);
        Database.roomsDB.add(room);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Room added Successfully!");
        alert.showAndWait();

        switchPane(manageRoomsPane);
    }
    public boolean validateAddRoom() {
        boolean valid = true;

        if(roomTypeField.getText().trim().isEmpty()) {
            roomTypeField.getStyleClass().add("error-field");
            roomTypeErrorLabel.setText("Can't be empty!");
            roomTypeErrorLabel.setVisible(true);
            valid = false;
        }

        try {
            Double value = Double.parseDouble(roomPriceField.getText());
        } catch (NumberFormatException e) {
            roomIdErrorLabel.getStyleClass().add("error-field");
            roomPriceErrorLabel.setText("Please enter a valid number");
            roomPriceErrorLabel.setVisible(true);
            valid = false;
        }

        if(availabilityComboBox.getValue() == null) {
            availabilityComboBox.getStyleClass().add("error-field");
            availabilityComboBoxErrorLabel.setVisible(true);
            valid = false;
        }

        return valid;
    }
    public void updateRoomAvailabilityButton(ActionEvent event) {
        switchPane(updateRoomAvailabilityPane);
    }
    public void confirmRoomAvailabilityChangeButton(ActionEvent event) {
        if(!validateRoomAvailability()) return;

        boolean availability = updateAvailabilityComboBox.getValue().toString().equalsIgnoreCase("Yes");

        if(Room.updateRoomAvailability(Integer.parseInt(roomIdField.getText()), availability)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Room Availability Updated successfully!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Couldn't find Room ID or encountered an error!");
            alert.showAndWait();
        }

        switchPane(manageRoomsPane);
    }
    public boolean validateRoomAvailability() {
        boolean valid = true;

        try {
            int value = Integer.parseInt(roomIdField.getText());
        } catch (NumberFormatException e) {
            roomIdField.getStyleClass().add("error-field");
            roomIdErrorLabel.setText("Please enter a valid number");
            roomIdErrorLabel.setVisible(true);
            valid = false;
        }

        if(updateAvailabilityComboBox.getValue() == null) {
            updateAvailabilityComboBox.getStyleClass().add("error-field");
            updateAvailabilityComboBoxErrorLabel.setVisible(true);
            valid = false;
        }

        return valid;
    }

    // manage categories
    public void manageCategories(ActionEvent event) {
        switchPane(manageCategoriesPane);
    }
    public void showCategoriesButton(ActionEvent event) {
        switchPane(showCategoriesPane);

        allCategories.setText(loggedInAdmin.showCategories());
    }
    public void addCategoryButton(ActionEvent event) {
        switchPane(addCategoryPane);

        categoryNameField.getStyleClass().removeAll("error-field");
        categoryNameErrorLabel.setVisible(false);
        categoryDescriptionField.getStyleClass().removeAll("error-field");
        categoryDescriptionErrorLabel.setVisible(false);
    }
    public void confirmAddCategoryButton(ActionEvent event) {
        categoryNameField.getStyleClass().removeAll("error-field");
        categoryNameErrorLabel.setVisible(false);
        categoryDescriptionField.getStyleClass().removeAll("error-field");
        categoryDescriptionErrorLabel.setVisible(false);

        if(Category.isNameUnique(categoryNameField.getText().trim())) {
            categoryNameField.getStyleClass().add("error-field");
            categoryNameErrorLabel.setText("Name already exists!");
            categoryNameErrorLabel.setVisible(true);

            return;
        }

        if(categoryNameField.getText().trim().isEmpty()) {
            categoryNameField.getStyleClass().add("error-field");
            categoryNameErrorLabel.setText("Can't be empty!");
            categoryNameErrorLabel.setVisible(true);

            return;
        }

        if(categoryDescriptionField.getText().trim().isEmpty()) {
            categoryDescriptionField.getStyleClass().add("error-field");
            categoryDescriptionErrorLabel.setText("Can't be empty!");
            categoryDescriptionErrorLabel.setVisible(true);

            return;
        }

        Category.create(new Category(categoryNameField.getText().trim(), categoryDescriptionField.getText().trim()));

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Category added Successfully!");
        alert.showAndWait();

        switchPane(manageCategoriesPane);
    }
    public void editCategoryButton(ActionEvent event) {
        switchPane(editCategoryPane);

        initialize();

        editCategoryNameField.getStyleClass().removeAll("error-field");
        editCategoryNameErrorLabel.setVisible(false);
        availableCategoryComboBox.getStyleClass().removeAll("error-field");
        availableCategoryErrorLabel.setVisible(false);
    }
    public void confirmEditAddCategoryButton(ActionEvent event) {
        editCategoryNameField.getStyleClass().removeAll("error-field");
        editCategoryNameErrorLabel.setVisible(false);
        availableCategoryComboBox.getStyleClass().removeAll("error-field");
        availableCategoryErrorLabel.setVisible(false);

        String selected = availableCategoryComboBox.getValue();
        if (selected == null || selected.isEmpty()) {
            availableCategoryComboBox.getStyleClass().add("error-field");
            availableCategoryErrorLabel.setText("Must select a category!");
            availableCategoryErrorLabel.setVisible(true);

            return;
        }

        if(Category.isNameUnique(editCategoryNameField.getText().trim())) {
            editCategoryNameField.getStyleClass().add("error-field");
            editCategoryNameErrorLabel.setText("Name already exists!");
            editCategoryNameErrorLabel.setVisible(true);

            return;
        }

        if(editCategoryNameField.getText().trim().isEmpty()) return;
        if(editCategoryDescriptionField.getText().trim().isEmpty()) return;

        Category.update(availableCategoryComboBox.getValue(), new Category(categoryNameField.getText().trim(), categoryDescriptionField.getText().trim()));

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Category Edited Successfully!");
        alert.showAndWait();

        editAvailableCategoryComboBox.setValue(null);
        editCategoryNameField.setText("");
        editCategoryDescriptionField.setText("");

        switchPane(manageCategoriesPane);
    }
    public void deleteCategoryButton(ActionEvent event) {
        switchPane(deleteCategoryPane);

        initialize();

        editAvailableCategoryComboBox.getStyleClass().removeAll("error-field");
        editAvailableCategoryErrorLabel.setVisible(false);
    }
    public void confirmDeleteCategoryButton(ActionEvent event) {
        availableCategoryComboBox.getStyleClass().removeAll("error-field");
        availableCategoryErrorLabel.setVisible(false);

        if(editAvailableCategoryComboBox.getValue().isEmpty()) {
            editAvailableCategoryComboBox.getStyleClass().add("error-field");
            editAvailableCategoryErrorLabel.setText("Must select a category!");
            editAvailableCategoryErrorLabel.setVisible(false);

            return;
        }

        Category.delete(availableCategoryComboBox.getValue());

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Category Deleted Successfully!");
        alert.showAndWait();

        switchPane(manageCategoriesPane);
    }

    // logout
    public void logout(ActionEvent event) throws IOException {
        Utility.loadScene("/views/LoginView.fxml", event);
    }
}
