package com.library.Model;

public class User {
    protected String username;
    protected String id;

    public User(String username, String id){
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }
}
