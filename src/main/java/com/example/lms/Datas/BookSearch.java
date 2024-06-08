package com.example.lms.Datas;
public class BookSearch {
    private int ID;
    private String Title;
    private String Author;
    private String Publisher;
    private int userId;

    public BookSearch(int ID, String Title, String Author, String Publisher, int userId) {
        this.ID = ID;
        this.Title = Title;
        this.Author = Author;
        this.Publisher = Publisher;
        this.userId = userId;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String Author) {
        this.Author = Author;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String Publisher) {
        this.Publisher = Publisher;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
