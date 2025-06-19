package com.library.Model;

public abstract class User {
    private String username;
    private String id;

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
    public void setId(String id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
