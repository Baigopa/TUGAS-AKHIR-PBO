package com.main;

import books.Book;
import books.HistoryBook;
import books.StoryBook;
import books.TextBook;
import data.Admin;
import data.Student;
import data.User;
import exception.custom.IllegalAdminAccess;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    public static ArrayList<Book> bookList = new ArrayList<>();
    public static ArrayList<User> userList = new ArrayList<>();
    static Admin admin = new Admin();

    public static void main(String[] args) {
        initializeData();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        showMainMenu(primaryStage);
    }

    private void showMainMenu(Stage stage) {
            // Create components
            Label titleLabel = new Label("Library System");
            titleLabel.getStyleClass().add("title");
    
            Button studentLoginButton = new Button("Login as Student");
            Button adminLoginButton = new Button("Login as Admin");
            Button exitButton = new Button("Exit");
    
            // Set button actions
            studentLoginButton.setOnAction(e -> enterNim(stage));
            adminLoginButton.setOnAction(e -> showAdminLogin(stage));
            exitButton.setOnAction(e -> System.exit(0));
    
            // Layout setup with GridPane
            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setHgap(10);
            gridPane.setVgap(20);
            gridPane.setPadding(new Insets(50));
    
            // Add components to GridPane
            gridPane.add(titleLabel, 0, 0, 2, 1);
            gridPane.add(studentLoginButton, 0, 1);
            gridPane.add(adminLoginButton, 1, 1);
            gridPane.add(exitButton, 0, 2, 2, 1);
    
            // Add CSS styles
            gridPane.getStyleClass().add("main-menu");
    
            // Create scene
            Scene scene = new Scene(gridPane, 400, 300);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    
            // Set stage properties
            stage.setTitle("Library System");
            stage.setScene(scene);
            stage.show();
        }

        private void enterNim(Stage stage) {
            Label nimLabel = new Label("Enter NIM:");
            TextField nimField = new TextField();
            Button submitButton = new Button("Submit");
            Button backButton = new Button("Back");
    
            // Set ID for CSS styling
            nimLabel.setId("nimLabel");
            nimField.setId("nimField");
            submitButton.setId("submitButton");
            backButton.setId("backButton");
    
            submitButton.setOnAction(e -> {
                String nim = nimField.getText();
                if (checkNim(nim)) {
                    User user = getUser(nim);
                    if (user != null) {
                        Student student = new Student(user.getName(), user.getNim(), user.getFaculty(), user.getProgram());
                        student.menu();
                    }
                } else {
                    showError(stage, "Invalid NIM. Please try again.");
                }
            });
            
            backButton.setOnAction(e -> showMainMenu(stage));
    
            // Create a GridPane layout
            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setPadding(new Insets(20));
            gridPane.setHgap(10);
            gridPane.setVgap(10);
    
            // Add components to the grid
            gridPane.add(nimLabel, 0, 0);
            gridPane.add(nimField, 1, 0);
            gridPane.add(submitButton, 0, 1);
            gridPane.add(backButton, 1, 1);
    
            // Create a scene with the grid pane
            Scene scene = new Scene(gridPane, 400, 250);
            // Apply CSS
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    
            stage.setScene(scene);
            stage.setTitle("NIM Entry");
            stage.show();
        }

    private void showAdminLogin(Stage stage) {
        Label usernameLabel = new Label("Username : ");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password : ");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button backButton = new Button("Back");

        // Set ID for CSS styling
        usernameLabel.setId("usernameLabel");
        usernameField.setId("usernameField");
        passwordLabel.setId("passwordLabel");
        passwordField.setId("passwordField");
        loginButton.setId("loginButton");
        backButton.setId("backButton");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            try {
                if (admin.isAdmin(username, password)) {
                    admin.adminMenu(stage);
                }
            } catch (Exception ex) {
                showError(stage, ex.getMessage());
            }
        });

        backButton.setOnAction(e -> showMainMenu(stage));

        // Create a GridPane layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add components to the grid
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 0, 2);
        gridPane.add(backButton, 1, 2);

        // Create a scene with the grid pane
        Scene scene = new Scene(gridPane, 350, 250);
        // Apply CSS
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Admin Login");
        stage.show();
    }

    private void showError(Stage stage, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(stage);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean checkNim(String nim) {
        for (User user : userList) {
            if (user instanceof Student && ((Student) user).getNim().equals(nim)) {
                return true;
            }
        }
        return false;
    }

    public static User getUser(String nim) {
        for (User user : userList) {
            if (user instanceof Student && ((Student) user).getNim().equals(nim)) {
                return user;
            }
        }
        return null;
    }

    public static void initializeData() {
        bookList.add(new HistoryBook("388c-e681-9152", "title1", "author1", "Sejarah", 8, 7,null));
        bookList.add(new StoryBook("ed90-be30-5cdb", "title2", "author2", "Cerita", 11, 14,null));
        bookList.add(new TextBook("d95e-0c4a-9523", "title3", "author3", "Novel", 3, 30,null));

        userList.add(new Student("Taufiq Ramadhan", "202210370311288", "Teknik", "Informatika"));
        userList.add(new Student("Who", "200510370318521", "Teknik", "Informatika"));
        userList.add(new Student("Sutrisno Adit Pratama", "202210370311203", "Teknik", "Informatika"));
    }
}
