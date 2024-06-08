package com.example.lms.Datas;

public class Member {
    private String username;
    private String email;
    private String password;
    private String phone;
    private int read_books;


    public Member(String username, String email, String password, String phone,int read_books) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.read_books =read_books;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getReadBook(){return read_books;}

    public void setReadBooks(){this.read_books = read_books;}


}
