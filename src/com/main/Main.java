package com.main;

import books.Book;
import books.HistoryBook;
import books.StoryBook;
import books.TextBook;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import data.Admin;
import data.Student;
import data.User;
// import exception.custom.IllegalAdminAccess;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
// import java.util.LinkedHashMap;
// import java.util.Map;

public class Main extends Application {

    private static final String DATA_DIR = "database";
    private static final String BOOK_FILE = DATA_DIR + "/books.json";
    private static final String USER_FILE = DATA_DIR + "/users.json";
    private static Gson gson = new GsonBuilder()
    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
    .create();
    public static ArrayList<Book> bookList = new ArrayList<>();
    public static ArrayList<User> userList = new ArrayList<>();
    static Admin admin = new Admin();

    public static void main(String[] args) {
        createDataDirectory();
        initializeData();
        // for (User user : userList) {
        //     System.out.println(user);
        // }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        showMainMenu(primaryStage);
    }

    private void showMainMenu(Stage stage) {
        Label titleLabel = new Label("Library System");
        titleLabel.getStyleClass().add("title");

        Button studentLoginButton = new Button("Login as Student");
        Button adminLoginButton = new Button("Login as Admin");
        Button exitButton = new Button("Exit");

        studentLoginButton.setOnAction(e -> enterNim(stage));
        adminLoginButton.setOnAction(e -> showAdminLogin(stage));
        exitButton.setOnAction(e -> System.exit(0));

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(50));

        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.add(studentLoginButton, 0, 1);
        gridPane.add(adminLoginButton, 1, 1);
        gridPane.add(exitButton, 0, 2, 2, 1);

        gridPane.getStyleClass().add("main-menu");

        Scene scene = new Scene(gridPane, 400, 300);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setTitle("Library System");
        stage.setScene(scene);
        stage.show();
    }

    private void enterNim(Stage stage) {
        Label nimLabel = new Label("Enter NIM:");
        TextField nimField = new TextField();
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");

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
                    stage.close();
                    student.menu();
                }
            } else {
                showError(stage, "Invalid NIM. Please try again.");
            }
        });

        backButton.setOnAction(e -> showMainMenu(stage));

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(nimLabel, 0, 0);
        gridPane.add(nimField, 1, 0);
        gridPane.add(submitButton, 0, 1);
        gridPane.add(backButton, 1, 1);

        Scene scene = new Scene(gridPane, 400, 250);
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

        Scene scene = new Scene(gridPane, 350, 250);
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
            String userNim = user.getNim(); 
            if (userNim != null && userNim.equals(nim)) { 
                // System.out.println("NIM matched: " + nim);
                return true;
            }
        }
        return false;
    }
    
    public static User getUser(String nim) {
        for (User user : userList) {
            if (user.getNim() != null && user.getNim().equals(nim)) {
                return user;
            }
        }
        return null;
    }

    public static void initializeData() {
        bookList = readBooks();
        userList = readUsers();
        if (bookList == null || bookList.isEmpty()) {
            bookList = new ArrayList<>();
            bookList.add(new HistoryBook("388c-e681-9152", "title1", "author1", "Sejarah", 8, 0, null));
            bookList.add(new StoryBook("ed90-be30-5cdb", "title2", "author2", "Cerita", 11, 0, null));
            bookList.add(new TextBook("d95e-0c4a-9523", "title3", "author3", "Novel", 3, 0, null));
            saveBooks(); // Simpan data default ke file JSON
        }
        if (userList == null || userList.isEmpty()) {
            userList = new ArrayList<>();
            userList.add(new Student("Taufiq Ramadhan", "202210370311288", "Teknik", "Informatika"));
            userList.add(new Student("Who", "200510370318521", "Teknik", "Informatika"));
            userList.add(new Student("Sutrisno Adit Pratama", "3", "Teknik", "Informatika"));
            saveUsers(); // Simpan data default ke file JSON
        }
    }

    private static void createDataDirectory() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
    }

    private static ArrayList<Book> readBooks() {
        try (FileReader reader = new FileReader(BOOK_FILE)) {
            Type listType = new TypeToken<ArrayList<Book>>() {
            }.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static ArrayList<User> readUsers() {
    try {
        File file = new File("database/users.json");
        if (file.exists()) {
            FileReader reader = new FileReader(file);
            Gson gson = new Gson();
            User[] usersArray = gson.fromJson(reader, User[].class);
            reader.close();
            return new ArrayList<>(Arrays.asList(usersArray));
        } else {
            return new ArrayList<>(); 
        }
    } catch (IOException e) {
        e.printStackTrace();
        return new ArrayList<>(); 
    }
}

    

    public static void saveBooks() {
        try (FileWriter writer = new FileWriter(BOOK_FILE)) {
            gson.toJson(bookList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveUsers() {
        try (FileWriter writer = new FileWriter(USER_FILE)) {
            gson.toJson(userList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class LocalDateAdapter
            implements com.google.gson.JsonDeserializer<LocalDate>, com.google.gson.JsonSerializer<LocalDate> {
        public LocalDate deserialize(com.google.gson.JsonElement json, java.lang.reflect.Type typeOfT,
                com.google.gson.JsonDeserializationContext context) throws com.google.gson.JsonParseException {
            return LocalDate.parse(json.getAsString());
        }

        public com.google.gson.JsonElement serialize(LocalDate src, java.lang.reflect.Type typeOfSrc,
                com.google.gson.JsonSerializationContext context) {
            return new com.google.gson.JsonPrimitive(src.toString());
        }
    }
}

