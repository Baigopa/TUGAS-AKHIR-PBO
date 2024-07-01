package data;

import books.Book;
import books.HistoryBook;
import books.StoryBook;
import books.TextBook;
import javafx.scene.control.Alert;

import java.util.List;

import com.main.Main;
import exception.custom.IllegalAdminAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.iMenu;

public class Admin extends User implements iMenu {
    private static final String USERNAME = "a";
    private static final String PASSWORD = "a";

    public Admin() {
        super("Admin", "", "", "");
    }

    public boolean isAdmin(String username, String password) throws IllegalAdminAccess {
        if (username.equals(USERNAME) && password.equals(PASSWORD)) {
            return true;
        } else {
            throw new IllegalAdminAccess("Invalid credentials");
        }
    }

    @Override
    public void menu() {
        // adminMenu(Stage);
    }

    public void adminMenu(Stage stage) {
        Label label = new Label("===== Admin Menu =====");
        label.setId("menuLabel");
    
        Button addStudentButton = new Button("Tambahkan Student");
        addStudentButton.setId("menuButton");
    
        Button addBookButton = new Button("Tambahkan Buku");
        addBookButton.setId("menuButton");
    
        Button showStudentsButton = new Button("Tampilkan Student yang Terdaftar");
        showStudentsButton.setId("menuButton");
    
        Button showBooksButton = new Button("Tampilkan Buku yang Tersedia");
        showBooksButton.setId("menuButton");
    
        Button logoutButton = new Button("Logout");
        logoutButton.setId("menuButton");
    
        addStudentButton.setOnAction(e -> showAddStudentForm(stage));
        addBookButton.setOnAction(e -> showAddBookForm(stage));
        showStudentsButton.setOnAction(e -> displayRegisteredStudents(stage));
        showBooksButton.setOnAction(e -> displayBooks(stage));
        logoutButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Logout");
            alert.setHeaderText(null);
            alert.setContentText("Keluar dari akun admin.");
            alert.showAndWait();
            new Main().start(stage);
        });
    
        VBox vbox = new VBox(15, label, addStudentButton, addBookButton, showStudentsButton, showBooksButton, logoutButton);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
    
        // Add margins to buttons
        VBox.setMargin(addStudentButton, new Insets(5, 0, 5, 0));
        VBox.setMargin(addBookButton, new Insets(5, 0, 5, 0));
        VBox.setMargin(showStudentsButton, new Insets(5, 0, 5, 0));
        VBox.setMargin(showBooksButton, new Insets(5, 0, 5, 0));
        VBox.setMargin(logoutButton, new Insets(20, 0, 0, 0));
    
        Scene scene = new Scene(vbox, 400, 400);
        scene.getStylesheets().add(getClass().getResource("admin.css").toExternalForm());
    
        stage.setScene(scene);
        stage.show();
    }
    

    private void showAddStudentForm(Stage stage) {
        Label nameLabel = new Label("Enter student name: ");
        nameLabel.setId("formLabel");
    
        TextField nameField = new TextField();
        nameField.setId("formTextField");
    
        Label nimLabel = new Label("Enter student NIM (15 digits): ");
        nimLabel.setId("formLabel");
    
        TextField nimField = new TextField();
        nimField.setId("formTextField");
    
        Label facultyLabel = new Label("Enter student faculty: ");
        facultyLabel.setId("formLabel");
    
        TextField facultyField = new TextField();
        facultyField.setId("formTextField");
    
        Label programLabel = new Label("Enter student program: ");
        programLabel.setId("formLabel");
    
        TextField programField = new TextField();
        programField.setId("formTextField");
    
        Button backButton = new Button("Back");
        backButton.setId("formButton");

        Button submitButton = new Button("Submit");
        submitButton.setId("formButton");
    
        submitButton.setOnAction(e -> {
            String name = nameField.getText();
            String nim = nimField.getText();
            String faculty = facultyField.getText();
            String program = programField.getText();
            if (nim.length() == 15 && nim.matches("\\d+")) {
                addStudent(name, nim, faculty, program, stage);
            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid NIM", "NIM must be 15 digits.");
            }
        });
    
        backButton.setOnAction(e -> {
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.close();
            adminMenu(stage);
        });
    
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
    
        // Add nodes to GridPane
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(nimLabel, 0, 1);
        gridPane.add(nimField, 1, 1);
        gridPane.add(facultyLabel, 0, 2);
        gridPane.add(facultyField, 1, 2);
        gridPane.add(programLabel, 0, 3);
        gridPane.add(programField, 1, 3);
    
        HBox buttonBox = new HBox(10,backButton,submitButton);
        buttonBox.setAlignment(Pos.CENTER);
        gridPane.add(buttonBox, 0, 4, 2, 1);
    
        Scene scene = new Scene(gridPane, 400, 400);
        scene.getStylesheets().add(getClass().getResource("admin.css").toExternalForm());
    
        stage.setScene(scene);
        stage.show();
    }
    
    
    private void showAddBookForm(Stage stage) {
        Label categoryLabel = new Label("Select book category:");
        categoryLabel.setId("formLabel");
    
        Button storyButton = new Button("Story Book");
        storyButton.setId("formButton");
    
        Button historyButton = new Button("History Book");
        historyButton.setId("formButton");
    
        Button textButton = new Button("Text Book");
        textButton.setId("formButton");
    
        Button backButton = new Button("Back");
        backButton.setId("formButton");
    
        storyButton.setOnAction(e -> showAddBookDetails(stage, "Story"));
        historyButton.setOnAction(e -> showAddBookDetails(stage, "History"));
        textButton.setOnAction(e -> showAddTextBookDetails(stage));
        backButton.setOnAction(e -> adminMenu(stage));
    
        VBox vbox = new VBox(10, categoryLabel, storyButton, historyButton, textButton, backButton);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
    
        // Add margins to buttons
        VBox.setMargin(storyButton, new Insets(5, 0, 5, 0));
        VBox.setMargin(historyButton, new Insets(5, 0, 5, 0));
        VBox.setMargin(textButton, new Insets(5, 0, 5, 0));
        VBox.setMargin(backButton, new Insets(20, 0, 0, 0));
    
        Scene scene = new Scene(vbox, 400, 400);
        scene.getStylesheets().add(getClass().getResource("admin.css").toExternalForm());
    
        stage.setScene(scene);
        stage.show();
    }
    
    private void showAddBookDetails(Stage stage, String category) {
    Label titleLabel = new Label("Enter book title: ");
    titleLabel.setId("formLabel");

    TextField titleField = new TextField();
    titleField.setId("formTextField");

    Label authorLabel = new Label("Enter author: ");
    authorLabel.setId("formLabel");

    TextField authorField = new TextField();
    authorField.setId("formTextField");

    Label stockLabel = new Label("Enter the stock: ");
    stockLabel.setId("formLabel");

    TextField stockField = new TextField();
    stockField.setId("formTextField");

    Button backButton = new Button("Back");
    backButton.setId("formButton");

    Button submitButton = new Button("Submit");
    submitButton.setId("formButton");


    submitButton.setOnAction(e -> {
        String title = titleField.getText();
        String author = authorField.getText();
        int stock = Integer.parseInt(stockField.getText());
        addBook(category, title, author, stock, 0, stage);
    });

    backButton.setOnAction(e -> showAddBookForm(stage));

    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(20));
    gridPane.setVgap(10);
    gridPane.setHgap(10);
    gridPane.setAlignment(Pos.CENTER);

    // Add nodes to GridPane
    gridPane.add(titleLabel, 0, 0);
    gridPane.add(titleField, 1, 0);
    gridPane.add(authorLabel, 0, 1);
    gridPane.add(authorField, 1, 1);
    gridPane.add(stockLabel, 0, 2);
    gridPane.add(stockField, 1, 2);

    HBox buttonBox = new HBox(10,backButton, submitButton);
    buttonBox.setAlignment(Pos.CENTER);
    gridPane.add(buttonBox, 0, 3, 2, 1);

    Scene scene = new Scene(gridPane, 400, 400);
    scene.getStylesheets().add(getClass().getResource("admin.css").toExternalForm());

    stage.setScene(scene);
    stage.show();
}

private void showAddTextBookDetails(Stage stage) {
    Label titleLabel = new Label("Enter book title: ");
    titleLabel.setId("formLabel");

    TextField titleField = new TextField();
    titleField.setId("formTextField");

    Label authorLabel = new Label("Enter author: ");
    authorLabel.setId("formLabel");

    TextField authorField = new TextField();
    authorField.setId("formTextField");

    Label stockLabel = new Label("Enter the stock: ");
    stockLabel.setId("formLabel");

    TextField stockField = new TextField();
    stockField.setId("formTextField");

    Button backButton = new Button("Back");
    backButton.setId("formButton");

    Button submitButton = new Button("Submit");
    submitButton.setId("formButton");


    submitButton.setOnAction(e -> {
        String title = titleField.getText();
        String author = authorField.getText();
        int stock = Integer.parseInt(stockField.getText());
        addBook("Text", title, author, stock, 0, stage);
    });

    backButton.setOnAction(e -> showAddBookForm(stage));

    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(20));
    gridPane.setVgap(10);
    gridPane.setHgap(10);
    gridPane.setAlignment(Pos.CENTER);

    // Add nodes to GridPane
    gridPane.add(titleLabel, 0, 0);
    gridPane.add(titleField, 1, 0);
    gridPane.add(authorLabel, 0, 1);
    gridPane.add(authorField, 1, 1);
    gridPane.add(stockLabel, 0, 2);
    gridPane.add(stockField, 1, 2);

    HBox buttonBox = new HBox(10, backButton,submitButton);
    buttonBox.setAlignment(Pos.CENTER);
    gridPane.add(buttonBox, 0, 3, 2, 1);

    Scene scene = new Scene(gridPane, 400, 400);
    scene.getStylesheets().add(getClass().getResource("admin.css").toExternalForm());

    stage.setScene(scene);
    stage.show();
}


    public void addBook(String category, String title, String author, int stock, int duration, Stage stage) {
        Book book;
        switch (category) {
            case "Story":
                book = new StoryBook(generateId(), title, author, category, stock, 0, null);
                break;
            case "History":
                book = new HistoryBook(generateId(), title, author, category, stock, 0, null);
                break;
            case "Text":
                book = new TextBook(generateId(), title, author, category, stock, 0, null);
                break;
            default:
                // Kategori buku tidak valid
                return;
        }
        Main.bookList.add(book);
        Main.saveBooks();
        // Tampilkan pesan berhasil
        showAlert(AlertType.INFORMATION, "Success", "Book added successfully.");
    }

    public void addStudent(String name, String nim, String faculty, String program, Stage stage) {
        Student student = new Student(name, nim, faculty, program);
        Main.userList.add(student);
        Main.saveUsers();
        showAlert(AlertType.INFORMATION, "Success", "Student added successfully.");
    }

    @SuppressWarnings("unchecked")
    public void displayRegisteredStudents(Stage stage) {
        ObservableList<User> usersData = FXCollections.observableArrayList();
        List<User> users = Main.readUsers();
        if (users != null) {
            usersData.addAll(users);
        }

        TableView<User> tableView = new TableView<>(usersData);
        tableView.setId("booksTable"); 

        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> nimColumn = new TableColumn<>("NIM");
        nimColumn.setCellValueFactory(new PropertyValueFactory<>("nim"));

        TableColumn<User, String> facultyColumn = new TableColumn<>("Faculty");
        facultyColumn.setCellValueFactory(new PropertyValueFactory<>("faculty"));

        TableColumn<User, String> programColumn = new TableColumn<>("Program");
        programColumn.setCellValueFactory(new PropertyValueFactory<>("program"));

        tableView.getColumns().addAll(nameColumn, nimColumn, facultyColumn, programColumn);

        Button backButton = new Button("Back");
        backButton.setId("formButton");
        backButton.setOnAction(e -> adminMenu(stage));

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(tableView, backButton);

        Scene scene = new Scene(vbox, 600, 400);
        scene.getStylesheets().add(getClass().getResource("admin.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @SuppressWarnings("unchecked")
    public void displayBooks(Stage stage) {
        TableView<Book> table = new TableView<>();
        table.setId("booksTable"); 
    
        TableColumn<Book, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    
        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
    
        TableColumn<Book, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
    
        TableColumn<Book, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    
        table.getColumns().addAll(idColumn, titleColumn, authorColumn, categoryColumn, stockColumn);
    
        ObservableList<Book> books = FXCollections.observableArrayList(Main.bookList);
        table.setItems(books);
    
        Button backButton = new Button("Back");
        backButton.setId("formButton");
        backButton.setOnAction(e -> adminMenu(stage));
    
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(table, backButton);
    
        Scene scene = new Scene(vbox, 600, 400);
        scene.getStylesheets().add(getClass().getResource("admin.css").toExternalForm());
    
        stage.setScene(scene);
        stage.show();
    }
    

    public String generateId() {
        String uuid = java.util.UUID.randomUUID().toString();
        return uuid.substring(0, 14);
    }

    protected void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
