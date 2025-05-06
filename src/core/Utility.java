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
    private static final Scanner scanner = new Scanner(System.in);

    public static String promptInput(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public static int promptInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number!\nTry Again...");
            }
        }
    }
    public static double promptDouble(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number!\nTry Again...");
            }
        }
    }
    public static LocalDate promptDate(String message) {
        while (true) {
            System.out.print(message);
            try {
                LocalDate dob = LocalDate.parse(scanner.nextLine());
                int age = Period.between(dob, LocalDate.now()).getYears();

                if (age >= 21) {
                    return dob;
                } else {
                    System.out.println("Age must be 21 or over!\nYour age is: " + age + "\nTry Again...");
                }
            } catch (Exception e) {
                System.out.println("Invalid date format!\nPlease use (yyyy-MM-dd).\nTry Again...");
            }
        }
    }

    public static String promptPassword(String message) {
        while (true) {
            System.out.print(message);
            String password = scanner.nextLine();
            if (password.length() >= 8) return password;
            System.out.println("Invalid Password!\nTry Again...");
        }
    }
    public static String[] promptInputInterests() {
        int count;
        while (true) {
            System.out.print("Enter the number of your interests (0–10): ");
            try {
                count = Integer.parseInt(scanner.nextLine());
                if (count >= 0 && count <= 10) break;
                System.out.println("Invalid input!\nPlease enter a number between 0 and 10.\nTry Again...");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input!\nPlease enter a number.\nTry Again...");
            }
        }

        String[] inputs = new String[count];
        for (int i = 0; i < count; i++) {
            System.out.print("Interest #" + (i + 1) + ": ");
            inputs[i] = scanner.nextLine();
        }

        return inputs;
    }
    public static int promptWorkingHours(String message) {
        while (true) {
            System.out.print(message);
            try {
                int temp = Integer.parseInt(scanner.nextLine());
                if (temp > 16 || temp < 2) {
                    System.out.println("Working Hours Have To Be Between 2-16 Hours!\nTry Again...");
                    continue;
                }
                else return temp;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number!\nTry Again...");
            }
        }
    }
    public static Gender promptGender() {
        while (true) {
            System.out.print("Gender (MALE/FEMALE/OTHER): ");
            String input = scanner.nextLine().toUpperCase();

            try {
                return Gender.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid gender!\nPlease enter MALE, FEMALE, or OTHER.\nTry Again...");
            }
        }
    }

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
