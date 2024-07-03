package data;

import java.util.ArrayList;
import java.util.List;

import com.main.Main;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import books.*;

public class User {
    protected String name;
    protected String nim;
    protected String faculty;
    protected String program;
    protected List<Book> borrowedBooks; // Added borrowedBooks field

    public User(String name, String nim, String faculty, String program) {
        this.name = name;
        this.nim = nim;
        this.faculty = faculty;
        this.program = program;
        this.borrowedBooks = new ArrayList<>(); // Initialize borrowedBooks
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getNim() {
        return nim;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getProgram() {
        return program;
    }

    // Method to borrow a book
    public void borrowBook(Book book) {
        borrowedBooks.add(book); // Add book to borrowedBooks
        Main.saveUsers(); 
    }

    // Method to return a book
    public void returnBook(Book book) {
        borrowedBooks.remove(book); // Remove book from borrowedBooks
    }

    // Method to display borrowed books
    public void displayBorrowedBooks() {
        StringBuilder sb = new StringBuilder();
        sb.append("Borrowed Books:\n");
        for (Book book : borrowedBooks) {
            sb.append(book.getTitle()).append("\n");
        }
        showAlert(AlertType.INFORMATION, "Borrowed Books", sb.toString());
    }

    // Method to logout
    public void logout() {
        showAlert(AlertType.INFORMATION, "Information", "Returning to main menu...");
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", nim='" + nim + '\'' +
                ", faculty='" + faculty + '\'' +
                ", program='" + program + '\'' +
                '}';
    }

    // Show alert method
    protected void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
