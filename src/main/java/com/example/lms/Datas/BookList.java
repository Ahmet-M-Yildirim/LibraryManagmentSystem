package com.example.lms.Datas;
public class BookList {
    private int book_id;
    private String book_title;
    private String author;
    private String publisher;
    private int user_id;

    public BookList(int id,String book_title, String author, String publisher,int user_id) {
        this.book_id = id;
        this.book_title = book_title;
        this.author = author;
        this.publisher = publisher;
        this.user_id = user_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public String getBook_title() {
        return book_title;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getBookId() {
        return book_id;
    }

    public void setBookId(int book_id) {
        this.book_id = book_id;
    }

    public String getBookTitle() {
        return book_title;
    }

    public void setBookTitle(String book_title) {
        this.book_title = book_title;
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

}