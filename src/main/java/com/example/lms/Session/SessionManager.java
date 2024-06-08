package com.example.lms.Session;

public class SessionManager {
    private static SessionManager instance;
    private int loggedInUserId;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public int getLoggedInUserId() {
        return loggedInUserId;
    }

    public void setLoggedInUserId(int userId) {
        this.loggedInUserId = userId;
    }
}