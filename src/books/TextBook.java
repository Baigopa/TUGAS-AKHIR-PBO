package books;

import java.time.LocalDate;

public class TextBook extends Book {

    public TextBook(String id, String title, String author, String category, int stock, int duration, LocalDate borrowDate) {
        super(id, title, author, category, stock, duration, borrowDate);
    }

}

