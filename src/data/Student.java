package data;

import java.util.ArrayList;
import books.Book;
import com.main.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.iMenu;

public class Student extends User implements iMenu {
    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    public Student(String name, String nim, String faculty, String program) {
        super(name, nim, faculty, program);
    }

    public void displayInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Student Information");
        alert.setHeaderText(null);
        alert.setContentText(
                "Name: " + name + "\n" +
                        "NIM: " + nim + "\n" +
                        "Faculty: " + faculty + "\n" +
                        "Program: " + program);
        alert.showAndWait();
    }

    @Override
    public void showBorrowedBooks() {
        TableView<Book> table = new TableView<>();

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Book, Integer> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        table.getColumns().addAll(titleColumn, authorColumn, categoryColumn, durationColumn);

        ObservableList<Book> borrowedBooksList = FXCollections.observableArrayList(borrowedBooks);
        table.setItems(borrowedBooksList);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(table);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.close();
            userMenu();
        });
        
        VBox.setMargin(backButton, new Insets(20, 0, 0, 0));
        vbox.getChildren().add(backButton);

        Scene scene = new Scene(vbox, 600, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void returnBooks() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
    
        if (borrowedBooks.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "No books borrowed yet.");
            return;
        }
    
        ObservableList<Book> tableData = FXCollections.observableArrayList(borrowedBooks);
        TableView<Book> tableView = new TableView<>(tableData);
    
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    
        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
    
        TableColumn<Book, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
    
        TableColumn<Book, Integer> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
    
        tableView.getColumns().addAll(titleColumn, authorColumn, categoryColumn, durationColumn);
    
        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> {
            Book selectedBook = tableView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                borrowedBooks.remove(selectedBook);
                selectedBook.setStock(selectedBook.getStock() + 1);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Book returned successfully.");
                Main.bookList.add(selectedBook);
                returnBooks(); 
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a book to return.");
            }
        });
    
        Button backButton = new Button("Back");
        
        backButton.setOnAction(e -> {
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.close();
            userMenu();
        });
        
    
        vbox.getChildren().addAll(tableView, returnButton, backButton);
    
        Scene scene = new Scene(vbox, 600, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    // @Override
    public void displayBooks() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
    
        ObservableList<Book> tableData = FXCollections.observableArrayList(Main.bookList);
        TableView<Book> tableView = new TableView<>(tableData);
    
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
    
        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
    
        TableColumn<Book, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
    
        TableColumn<Book, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    
        TableColumn<Book, Void> borrowColumn = new TableColumn<>("Action");
        borrowColumn.setCellFactory(param -> new TableCell<>() {
            final Button borrowButton = new Button("Borrow");
    
            {
                borrowButton.setOnAction(event -> {
                    Book book = getTableView().getItems().get(getIndex());
                    if (book.getStock() > 0) {
                        TextInputDialog dialog = new TextInputDialog();
                        dialog.setTitle("Borrow Book");
                        dialog.setHeaderText(null);
                        dialog.setContentText("How many days do you want to borrow the book? (maximum 14 days):");
                        dialog.showAndWait().ifPresent(daysStr -> {
                            try {
                                int days = Integer.parseInt(daysStr);
                                if (days <= 14) {
                                    borrowedBooks.add(new Book(book.getId(), book.getTitle(), book.getAuthor(),
                                            book.getCategory(), book.getStock(), days));
                                    book.setStock(book.getStock() - 1);
                                    showAlert(Alert.AlertType.INFORMATION, "Success", "Book borrowed successfully.");
                                    tableView.refresh();
                                } else {
                                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid number of days.");
                                }
                            } catch (NumberFormatException ex) {
                                showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid number.");
                            }
                        });
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Book out of stock!");
                    }
                });
            }
    
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(borrowButton);
                }
            }
        });
    
        tableView.getColumns().addAll(titleColumn, authorColumn, categoryColumn, stockColumn, borrowColumn);
    
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.close();
            userMenu();
        });
    
        vbox.getChildren().addAll(tableView, backButton);
    
        Scene scene = new Scene(vbox, 600, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void menu() {
        userMenu();
    }

    @Override
    public void logout() {
        showAlert(Alert.AlertType.INFORMATION, "Logout", "Returning to main menu...");
        new Main().start(new Stage());
    }

    private void userMenu() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        
        Label label = new Label("===== Student menu =====");
        Button borrowedBooksButton = new Button("Show Borrowed Books");
        borrowedBooksButton.setOnAction(e -> showBorrowedBooks());

        Button borrowBookButton = new Button("Borrow Book");
        borrowBookButton.setOnAction(e -> displayBooks());

        Button returnBookButton = new Button("Return Book");
        returnBookButton.setOnAction(e -> returnBooks());

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> logout());
        
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        vbox.getChildren().addAll(label,borrowedBooksButton, borrowBookButton, returnBookButton, logoutButton);

        Scene scene = new Scene(vbox, 400, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    protected void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
