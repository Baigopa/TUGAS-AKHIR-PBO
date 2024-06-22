package data;

import books.Book;
import books.HistoryBook;
import books.StoryBook;
import books.TextBook;
import javafx.scene.control.Alert;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.iMenu;

public class Admin extends User implements iMenu {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin123";

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
        Button addStudentButton = new Button("Tambahkan Student");
        Button addBookButton = new Button("Tambahkan Buku");
        Button showStudentsButton = new Button("Tampilkan Student yang Terdaftar");
        Button showBooksButton = new Button("Tampilkan Buku yang Tersedia");
        Button logoutButton = new Button("Logout");

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

        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        VBox vbox = new VBox(15, label, addStudentButton, addBookButton, showStudentsButton, showBooksButton,
                logoutButton);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        // Add margins to buttons
        VBox.setMargin(addStudentButton, new Insets(5, 0, 5, 0));
        VBox.setMargin(addBookButton, new Insets(5, 0, 5, 0));
        VBox.setMargin(showStudentsButton, new Insets(5, 0, 5, 0));
        VBox.setMargin(showBooksButton, new Insets(5, 0, 5, 0));
        VBox.setMargin(logoutButton, new Insets(20, 0, 0, 0));

        Scene scene = new Scene(vbox, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void showAddStudentForm(Stage stage) {
        Label nameLabel = new Label("Enter student name: ");
        TextField nameField = new TextField();
        Label nimLabel = new Label("Enter student NIM: ");
        TextField nimField = new TextField();
        Label facultyLabel = new Label("Enter student faculty: ");
        TextField facultyField = new TextField();
        Label programLabel = new Label("Enter student program: ");
        TextField programField = new TextField();
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");

        submitButton.setOnAction(e -> {
            String name = nameField.getText();
            String nim = nimField.getText();
            String faculty = facultyField.getText();
            String program = programField.getText();
            addStudent(name, nim, faculty, program, stage);
        });

        backButton.setOnAction(e -> {
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.close();
            adminMenu(stage);
        });

        nameLabel.setStyle("-fx-font-size: 14px;");
        nimLabel.setStyle("-fx-font-size: 14px;");
        facultyLabel.setStyle("-fx-font-size: 14px;");
        programLabel.setStyle("-fx-font-size: 14px;");

        VBox vbox = new VBox(10, nameLabel, nameField, nimLabel, nimField, facultyLabel, facultyField, programLabel,
                programField, submitButton, backButton);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        // Add margins to text fields and buttons
        VBox.setMargin(nameField, new Insets(5, 0, 10, 0));
        VBox.setMargin(nimField, new Insets(5, 0, 10, 0));
        VBox.setMargin(facultyField, new Insets(5, 0, 10, 0));
        VBox.setMargin(programField, new Insets(5, 0, 10, 0));
        VBox.setMargin(submitButton, new Insets(20, 0, 5, 0));
        VBox.setMargin(backButton, new Insets(5, 0, 20, 0));

        Scene scene = new Scene(vbox, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void showAddBookForm(Stage stage) {
        Label categoryLabel = new Label("Select book category:");
        Button storyButton = new Button("Story Book");
        Button historyButton = new Button("History Book");
        Button textButton = new Button("Text Book");
        Button backButton = new Button("Back");

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
        stage.setScene(scene);
        stage.show();
    }

    private void showAddBookDetails(Stage stage, String category) {
        Label titleLabel = new Label("Enter book title: ");
        TextField titleField = new TextField();
        Label authorLabel = new Label("Enter author: ");
        TextField authorField = new TextField();
        Label stockLabel = new Label("Enter the stock: ");
        TextField stockField = new TextField();
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");

        submitButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            int stock = Integer.parseInt(stockField.getText());
            addBook(category, title, author, stock, 0, stage);
        });

        backButton.setOnAction(e -> showAddBookForm(stage));

        titleLabel.setStyle("-fx-font-size: 14px;");
        authorLabel.setStyle("-fx-font-size: 14px;");
        stockLabel.setStyle("-fx-font-size: 14px;");

        VBox vbox = new VBox(10, titleLabel, titleField, authorLabel, authorField, stockLabel, stockField, submitButton,
                backButton);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        // Add margins to text fields and buttons
        VBox.setMargin(titleField, new Insets(5, 0, 10, 0));
        VBox.setMargin(authorField, new Insets(5, 0, 10, 0));
        VBox.setMargin(stockField, new Insets(5, 0, 10, 0));
        VBox.setMargin(submitButton, new Insets(20, 0, 5, 0));
        VBox.setMargin(backButton, new Insets(5, 0, 20, 0));

        Scene scene = new Scene(vbox, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void showAddTextBookDetails(Stage stage) {
        Label titleLabel = new Label("Enter book title: ");
        TextField titleField = new TextField();
        Label authorLabel = new Label("Enter author: ");
        TextField authorField = new TextField();
        Label stockLabel = new Label("Enter the stock: ");
        TextField stockField = new TextField();
        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");

        submitButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            int stock = Integer.parseInt(stockField.getText());
            addBook("Text", title, author, stock, 0, stage);
        });

        backButton.setOnAction(e -> showAddBookForm(stage));

        titleLabel.setStyle("-fx-font-size: 14px;");
        authorLabel.setStyle("-fx-font-size: 14px;");
        stockLabel.setStyle("-fx-font-size: 14px;");

        VBox vbox = new VBox(10, titleLabel, titleField, authorLabel, authorField, stockLabel, stockField, submitButton,
                backButton);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);

        // Add margins to text fields and buttons
        VBox.setMargin(titleField, new Insets(5, 0, 10, 0));
        VBox.setMargin(authorField, new Insets(5, 0, 10, 0));
        VBox.setMargin(stockField, new Insets(5, 0, 10, 0));
        VBox.setMargin(submitButton, new Insets(20, 0, 5, 0));
        VBox.setMargin(backButton, new Insets(5, 0, 20, 0));

        Scene scene = new Scene(vbox, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    public void addBook(String category, String title, String author, int stock, int duration, Stage stage) {
        Book book;
        switch (category) {
            case "Story":
                book = new StoryBook(generateId(), title, author, category, stock, 0,null);
                break;
            case "History":
                book = new HistoryBook(generateId(), title, author, category, stock, 0,null);
                break;
            case "Text":
                book = new TextBook(generateId(), title, author, category, stock, 0,null);
                break;
            default:
                // Kategori buku tidak valid
                return;
        }
        Main.bookList.add(book);
        // Tampilkan pesan berhasil
        showAlert(AlertType.INFORMATION, "Success", "Book added successfully.");
    }

    public void addStudent(String name, String nim, String faculty, String program, Stage stage) {
        Student student = new Student(name, nim, faculty, program);
        Main.userList.add(student);
        showAlert(AlertType.INFORMATION, "Success", "Student added successfully.");
    }

    public void displayRegisteredStudents(Stage stage) {
        TableView<Student> table = new TableView<>();

        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, String> nimColumn = new TableColumn<>("NIM");
        nimColumn.setCellValueFactory(new PropertyValueFactory<>("nim"));

        TableColumn<Student, String> facultyColumn = new TableColumn<>("Faculty");
        facultyColumn.setCellValueFactory(new PropertyValueFactory<>("faculty"));

        TableColumn<Student, String> programColumn = new TableColumn<>("Program");
        programColumn.setCellValueFactory(new PropertyValueFactory<>("program"));

        table.getColumns().addAll(nameColumn, nimColumn, facultyColumn, programColumn);

        ObservableList<Student> students = FXCollections.observableArrayList();
        for (User user : Main.userList) {
            if (user instanceof Student) {
                students.add((Student) user);
            }
        }
        table.setItems(students);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> adminMenu(stage));

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(table, backButton);

        Scene scene = new Scene(vbox, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public void displayBooks(Stage stage) {
        TableView<Book> table = new TableView<>();

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
        backButton.setOnAction(e -> adminMenu(stage));

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(table, backButton);

        Scene scene = new Scene(vbox, 600, 400);
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
