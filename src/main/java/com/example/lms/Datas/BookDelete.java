package com.example.lms.Datas;
public class BookDelete {
    private int book_id;
    private String book_title;
    private String author;
    private String publisher;
    private String page_number;
    private String categories;
    private int user_id;

    public BookDelete(int book_id, String book_title, String author, String publisher, String page_number, String categories, int user_id) {
        this.book_id = book_id;
        this.book_title = book_title;
        this.author = author;
        this.publisher = publisher;
        this.page_number = page_number;
        this.categories = categories;
        this.user_id = user_id;
    }

    public int getBookId() {
        return book_id;
    }

    public String getBookTitle() {
        return book_title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPage_number() {
        return page_number;
    }

    public String getCategories() {
        return categories;
    }

    public int getUserId() {
        return user_id;
    }
}

