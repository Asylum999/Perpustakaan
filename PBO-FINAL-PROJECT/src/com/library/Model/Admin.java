package com.library.Model;

public class Admin extends User{
    public Admin(String username, String id){
        super(username,id);
    }
    @Override
    public void login() {
        if (authenticate()) {
            System.out.println("Admin login successful");
            displayinfo();
        } else {
            System.out.println("Invalid admin credentials");
        }
    }

    @Override
    public void displayinfo() {
        System.out.println("\nAdmin Information");
        System.out.println("------------------");
        System.out.println("Username : " + getUsername());
        System.out.println("ID       : " + getId());
    }
}
