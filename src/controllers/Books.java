package controllers;

public class Books {
    private String bookId;
    private String title;
    public Books(String bookId, String title) {
        this.bookId = bookId;
        this.title = title;
    }
    public Books(String title) {
        this.title = title;
    }
    private String author;
    private String year;
    private String pages;
    private String category;
    public Books(String bookId, String title, String author, String year, String pages, String category) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
        this.category = category;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
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
    @Override
    public String toString(){
        return bookId + "-"+ title;
        
    }
    
    
}
