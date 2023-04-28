package controllers;

public class Books {
    private String bookId;
    private String title;
    private String author;
    private String year;
    private String pages;
    public Books(String bookId, String title, String author, String year, String pages) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
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
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getPages() {
        return pages;
    }
    public void setPages(String pages) {
        this.pages = pages;
    }
    
    
}
