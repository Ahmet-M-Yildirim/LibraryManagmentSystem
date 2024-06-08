package com.example.lms.Datas;

public class MemberRB {

    private String username;
    private int read_books;
    private int no;
    public MemberRB(int no, String username, int read_books){
            this.no = no;
            this.read_books = read_books;
            this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getRead_books() {
        return read_books;
    }

    public void setRead_books(int read_books) {
        this.read_books = read_books;
    }
}
