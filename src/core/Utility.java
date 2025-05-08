package core;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.Scanner;

public class Utility {
    public static void loadScene(String fxmlPath, ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Utility.class.getResource(fxmlPath)));
            Parent root = loader.load();
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
