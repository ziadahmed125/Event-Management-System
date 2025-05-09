package controllers.menus;

import actors.Admin;
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
    @FXML private VBox deleteRoomPane;
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
    @FXML private Label setWorkingHoursErrorLabel;

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
    @FXML private Label deleteRoomErrorLabel;
    @FXML private Label roomInfoLabel;
    @FXML private TextField roomTypeField;
    @FXML private TextField roomPriceField;
    @FXML private ComboBox<Room> deleteRoomComboBox;

    // manage categories
    @FXML private Label allCategories;
    @FXML private TextField categoryNameField;
    @FXML private TextField categoryDescriptionField;
    @FXML private Label categoryNameErrorLabel;
    @FXML private Label categoryDescriptionErrorLabel;
    @FXML private TextField editCategoryNameField;
    @FXML private TextField editCategoryDescriptionField;
    @FXML private Label editCategoryNameErrorLabel;
    @FXML private ComboBox<Category> editAvailableCategoryComboBox;
    @FXML private ComboBox<Category> deleteCategoryComboBox;
    @FXML private Label editAvailableCategoryErrorLabel;
    @FXML private Label deleteCategoryErrorLabel;

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
    }

    public void confirmRole(ActionEvent event) {
        setRoleField.getStyleClass().removeAll("error-field");
        setRoleErrorLabel.setVisible(false);

        if(setRoleField.getText().trim().isEmpty()) {
            setRoleField.getStyleClass().add("error-field");
            setRoleErrorLabel.setText("Field can't be empty!");
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
        setWorkingHoursErrorLabel.setVisible(false);

        if(setWorkingHoursField.getText().trim().isEmpty()) {
            setWorkingHoursField.getStyleClass().add("error-field");
            setWorkingHoursErrorLabel.setText("Filed can't be empty!");
            setRoleField.setVisible(true);

            return;
        }

        try {
            Integer.parseInt(setWorkingHoursField.getText().trim());

        } catch (NumberFormatException e) {
            setWorkingHoursField.getStyleClass().add("error-field");
            setWorkingHoursErrorLabel.setText("Must be a number!");
            setWorkingHoursField.setVisible(true);

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
        if (!firstNameField.getText().trim().isEmpty()
                && firstNameField.getText().trim().equals(loggedInAdmin.getFirstName())) {
            firstNameField.getStyleClass().add("error-field");
            firstNameErrorLabel.setText("Same First Name!");
            firstNameErrorLabel.setVisible(true);
            valid = false;
        }

        // Last Name
        if (!lastNameField.getText().trim().isEmpty()
                && lastNameField.getText().trim().equals(loggedInAdmin.getLastName())) {
            lastNameField.getStyleClass().add("error-field");
            lastNameErrorLabel.setText("Same Last Name!");
            lastNameErrorLabel.setVisible(true);
            valid = false;
        }

        // Username
        if (!usernameField.getText().trim().isEmpty()
                && usernameField.getText().trim().equals(loggedInAdmin.getUsername())) {
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
                && dobField.getValue().equals(loggedInAdmin.getDateOfBirth())) {
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
                && addressField.getText().trim().equals(loggedInAdmin.getAddress())) {
            addressField.getStyleClass().add("error-field");
            addressErrorLabel.setText("Same address!");
            addressErrorLabel.setVisible(true);
            valid = false;
        }

        // Email
        String email = emailField.getText().trim();
        if (!email.isEmpty()
                && email.equals(loggedInAdmin.getEmail())) {
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
        if (!password.trim().isEmpty()
                && password.trim().equals(loggedInAdmin.getPassword())) {
            passwordField.getStyleClass().add("error-field");
            passwordErrorLabel.setText("Same Password!");
            passwordErrorLabel.setVisible(true);
            valid = false;
        }
        else if (!password.trim().isEmpty()
                && password.length() < 8) {
            passwordField.getStyleClass().add("error-field");
            passwordErrorLabel.setText("Password must be over 8 characters!");
            passwordErrorLabel.setVisible(true);
            valid = false;
        }

        // Confirm Password
        if (!(confirmPasswordField.getText().trim().isEmpty() && password.trim().isEmpty()) && !confirmPasswordField.getText().equals(password)) {
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

        roomTypeField.getStyleClass().removeAll("error-field");
        roomPriceField.getStyleClass().removeAll("error-field");
        roomTypeErrorLabel.setVisible(false);
        roomPriceErrorLabel.setVisible(false);
    }
    public void confirmAddRoomButton(ActionEvent event) {
        roomTypeField.getStyleClass().removeAll("error-field");
        roomPriceField.getStyleClass().removeAll("error-field");
        roomTypeErrorLabel.setVisible(false);
        roomPriceErrorLabel.setVisible(false);

        if(!validateAddRoom()) return;

        Room room = new Room(true, roomTypeField.getText().trim(), Double.parseDouble(roomPriceField.getText()));
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

        if (roomPriceField.getText().trim().isEmpty()) {
            roomPriceField.getStyleClass().add("error-field");
            roomPriceErrorLabel.setText("Can't be empty!");
            roomPriceErrorLabel.setVisible(true);
            valid = false;
        } else {
            try {
                Double.parseDouble(roomPriceField.getText().trim());
            } catch (NumberFormatException e) {
                roomPriceField.getStyleClass().add("error-field");
                roomPriceErrorLabel.setText("Please enter a valid number");
                roomPriceErrorLabel.setVisible(true);
                valid = false;
            }
        }


        return valid;
    }
    public void deleteRoomButton(ActionEvent event) {
        switchPane(deleteRoomPane);

        deleteRoomComboBox.setItems(FXCollections.observableArrayList(Database.roomsDB));
        deleteRoomComboBox.getStyleClass().removeAll("error-field");
        deleteRoomErrorLabel.setVisible(false);
        roomInfoLabel.setText("");
    }
    public void confirmRoom(ActionEvent event) {
        deleteRoomComboBox.getStyleClass().removeAll("error-field");
        deleteRoomErrorLabel.setVisible(false);

        if(deleteRoomComboBox.getValue() == null) {
            deleteRoomComboBox.getStyleClass().add("error-field");
            deleteRoomErrorLabel.setText("Must select a room!");
            deleteRoomErrorLabel.setVisible(true);
            return;
        }

        roomInfoLabel.setText(deleteRoomComboBox.getValue().getRoomInfo());
    }
    public void confirmRoomDeleteButton(ActionEvent event) {
        deleteRoomComboBox.getStyleClass().removeAll("error-field");
        deleteRoomErrorLabel.setVisible(false);

        if(deleteRoomComboBox.getValue() == null) {
            deleteRoomComboBox.getStyleClass().add("error-field");
            deleteRoomErrorLabel.setText("Must select a room!");
            deleteRoomErrorLabel.setVisible(true);
            return;
        }

        deleteRoomComboBox.getValue().delete();

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Room Deleted Successfully!");
        alert.showAndWait();

        deleteRoomComboBox.setValue(null);

        switchPane(manageRoomsPane);
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

        editAvailableCategoryComboBox.setItems(FXCollections.observableArrayList(Database.categoriesDB));

        editCategoryNameField.getStyleClass().removeAll("error-field");
        editCategoryNameErrorLabel.setVisible(false);
        editAvailableCategoryComboBox.getStyleClass().removeAll("error-field");
        editAvailableCategoryErrorLabel.setVisible(false);
    }
    public void confirmEditCategoryButton(ActionEvent event) {
        // Reset styles and error labels
        editCategoryNameField.getStyleClass().removeAll("error-field");
        editCategoryNameErrorLabel.setVisible(false);
        editAvailableCategoryComboBox.getStyleClass().removeAll("error-field");
        editAvailableCategoryErrorLabel.setVisible(false);

        // Validation: must select a category
        if (editAvailableCategoryComboBox.getValue() == null) {
            editAvailableCategoryComboBox.getStyleClass().add("error-field");
            editAvailableCategoryErrorLabel.setText("Must select a category!");
            editAvailableCategoryErrorLabel.setVisible(true);
            return;
        }

        String newName = editCategoryNameField.getText().trim();
        String newDescription = editCategoryDescriptionField.getText().trim();
        Category selectedCategory = editAvailableCategoryComboBox.getValue();

        if (!newName.isEmpty() && !newName.equals(selectedCategory.getName()) &&
                Category.isNameUnique(newName)) {
            editCategoryNameField.getStyleClass().add("error-field");
            editCategoryNameErrorLabel.setText("Name already exists!");
            editCategoryNameErrorLabel.setVisible(true);
            return;
        }

        if (newName.isEmpty()) {
            newName = selectedCategory.getName();
        }
        if (newDescription.isEmpty()) {
            newDescription = selectedCategory.getDescription();
        }

        selectedCategory.setName(newName);
        selectedCategory.setDescription(newDescription);

        editAvailableCategoryComboBox.setItems(FXCollections.observableArrayList(Database.categoriesDB));
        deleteCategoryComboBox.setItems(FXCollections.observableArrayList(Database.categoriesDB));

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Category Edited Successfully!");
        alert.showAndWait();

        // Clear fields
        deleteCategoryComboBox.setValue(null);
        editCategoryNameField.setText("");
        editCategoryDescriptionField.setText("");

        switchPane(manageCategoriesPane);
    }
    public void deleteCategoryButton(ActionEvent event) {
        switchPane(deleteCategoryPane);

        deleteCategoryComboBox.setItems(FXCollections.observableArrayList(Database.categoriesDB));

        deleteCategoryComboBox.getStyleClass().removeAll("error-field");
        deleteCategoryErrorLabel.setVisible(false);
    }
    public void confirmDeleteCategoryButton(ActionEvent event) {
        deleteCategoryComboBox.getStyleClass().removeAll("error-field");
        deleteCategoryErrorLabel.setVisible(false);

        if(deleteCategoryComboBox.getValue() == null) {
            deleteCategoryComboBox.getStyleClass().add("error-field");
            deleteCategoryErrorLabel.setText("Must select a category!");
            deleteCategoryErrorLabel.setVisible(false);

            return;
        }

        deleteCategoryComboBox.getValue().delete();

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Category Deleted Successfully!");
        alert.showAndWait();

        switchPane(manageCategoriesPane);
    }

    // logout
    public void logout(ActionEvent event) throws IOException {
        Utility.loadScene("/views/LoginView.fxml", event);
    }
}
