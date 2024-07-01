package data;

// import books.Book;
// import com.main.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class User {
    protected String type = "user";
    protected String name;
    protected String nim;
    protected String faculty;
    protected String program;

    public User(String name, String nim, String faculty, String program) {
        this.name = name;
        this.nim = nim;
        this.faculty = faculty;
        this.program = program;
    }

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

    public void showBorrowedBooks() {
        showAlert(AlertType.INFORMATION, "Information", "No books borrowed yet. \nPlease borrow a book first.");
    }

    public void returnBooks() {
        showAlert(AlertType.INFORMATION, "Information", "No books borrowed yet.");
    }

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

    protected void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
