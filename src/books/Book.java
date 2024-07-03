package books;

import java.time.LocalDate;

public class Book {
    private String id;
    private String title;
    private String author;
    private String category;
    private int stock;
    private int duration;
    private LocalDate borrowDate;
    //Constructor 

    public Book(String id, String title, String author, String category, int stock, int duration, LocalDate borrowDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.stock = stock;
        this.duration = duration;
        this.borrowDate = borrowDate;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public int getStock() {
        return stock;
    }
    public int getDuration() {
        return duration;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public void setDuration(int duration) {
        this.duration = duration;
    }
}
