package com.library.Model;

public abstract class User {
    private String username;
    private String id;
    private String email;
    private String hashedId;


    public User(String username, String id) {
        this.username = username;
        this.email = email;
        this.hashedId = hashedId;

    }

    public User(String id, String username, String email, String hashedId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.hashedId = hashedId;
    }


    // Getters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedId() {
        return hashedId;
    }

    public void setHashedId(String hashedId) {
        this.hashedId = hashedId;
    }

public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Abstract methods that must be implemented by child classes
    public abstract void login();
    public abstract void displayinfo();

    // Common method for authentication
    protected boolean authenticate() {
        try (Connections conn = new Connections()) {
            User user = conn.verifyLogin(this.getId(), this.getUsername());
            return user != null && user.getClass() == this.getClass();
        } catch (Exception e) {
            System.err.println("Authentication error: " + e.getMessage());
            return false;
        }
    }


    // Common method for data persistence
    protected void saveChanges() {
        try (Connections conn = new Connections()) {
            conn.updateUser(this);
        } catch (Exception e) {
            System.err.println("Error saving changes: " + e.getMessage());
        }
    }


    // Override toString for better object representation
    @Override
    public String toString() {
        return String.format("User{type=%s, username='%s', id='%s'}",
                this.getClass().getSimpleName(),
                username,
                id);
    }

    // Override equals for proper object comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id.equals(user.id);
    }

    // Override hashCode for proper collection handling
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
