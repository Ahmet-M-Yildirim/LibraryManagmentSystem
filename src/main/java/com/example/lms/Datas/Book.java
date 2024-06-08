package com.example.lms.Datas;

public class Book {
    private String bookTitle;
    private String author;
    private String publisher;
    private String pageNumber;
    private String categories;
    private int userId;

    public Book(String bookTitle, String author, String publisher, String pageNumber, String categories, int userId) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.publisher = publisher;
        this.pageNumber = pageNumber;
        this.categories = categories;
        this.userId = userId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
