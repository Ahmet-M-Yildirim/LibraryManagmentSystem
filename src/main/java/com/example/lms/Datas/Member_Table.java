package com.example.lms.Datas;


public class Member_Table {

    int id;
    String Username;
    String email;


    public Member_Table(int id, String Username, String email) {
        this.id = id;
        this.Username = Username;
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return Username;
    }

    public String getEmail() {
        return email;
    }


    public void setUsername(String Username) {
        this.Username = Username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
