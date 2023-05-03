package controllers;

public class BookIssue {
    private String bookId;
    private String title;
    private String author;
    private String year;
    private String pages;
    private String category;
    private String isActive;
    public BookIssue(String bookId, String title, String author, String year, String pages, String category,
            String isActive) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
        this.category = category;
        this.isActive = isActive;
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
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getIsActive() {
        return isActive;
    }
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
