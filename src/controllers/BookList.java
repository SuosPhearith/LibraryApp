package controllers;

public class BookList {
    private String bookId;
    private String title;
    public BookList(String bookId, String title) {
        this.bookId = bookId;
        this.title = title;
    }
    public String getBookId() {
        return bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
