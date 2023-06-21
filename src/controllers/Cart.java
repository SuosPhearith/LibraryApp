package controllers;

public class Cart {
    private String borrowBookId;
    private String borrowId;
    private String bookId;
    private String bookName;
    public Cart(){}
    public Cart(String bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }
    public Cart(String borrowBookId, String borrowId, String bookId, String bookName) {
        this.borrowBookId = borrowBookId;
        this.borrowId = borrowId;
        this.bookId = bookId;
        this.bookName = bookName;
    }
    public String getBorrowBookId() {
        return borrowBookId;
    }
    public void setBorrowBookId(String borrowBookId) {
        this.borrowBookId = borrowBookId;
    }
    public String getBorrowId() {
        return borrowId;
    }
    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }
    public String getBookId() {
        return bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
