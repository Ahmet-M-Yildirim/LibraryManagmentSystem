package com.example.lms.Datas;


public class MemberBooks {
    private int userId;
    private String username;
    private String email;
    private String password;
    private String phone;
    private int readBooks;

    public MemberBooks(String username, String email, String password, String phone, int readBooks) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.readBooks = readBooks;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getReadBooks() {
        return readBooks;
    }

    public void setReadBooks(int readBooks) {
        this.readBooks = readBooks;
    }

    public void addReadBook() {
        this.readBooks++;
    }

    public void removeReadBook() {
        if (this.readBooks > 0) {
            this.readBooks--;
        }
    }
}
