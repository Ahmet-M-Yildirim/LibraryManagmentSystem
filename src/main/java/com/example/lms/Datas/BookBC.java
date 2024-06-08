package com.example.lms.Datas;

public class BookBC {
    private int no;
    private String book_title;
    private String author;
    private int bookCount;

    public BookBC(int no,String book_title, String author, int bookCount) {
        this.no = no;
        this.book_title = book_title;
        this.author = author;
        this.bookCount = bookCount;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
