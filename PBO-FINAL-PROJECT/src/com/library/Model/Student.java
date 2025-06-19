package com.library.Model;

public class Student extends User {
    private String faculty;
    private String major;
    private String email;

    public Student(String username, String id, String faculty, String major, String email) {
        super(username, id);
        this.faculty = faculty;
        this.major = major;
        this.email = email;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMajor() {
        return major;
    }
    public String getEmail() {
        return email;
    }
    public String getFaculty() {
        return faculty;
    }

    @Override
    public void login() {
        if (authenticate()) {
            System.out.println("Student login successful");
            displayinfo();
        } else {
            System.out.println("Invalid student credentials");
        }
    }

    @Override
    public void displayinfo() {  // Changed from displayInfo to match abstract class
        System.out.println("\nStudent Information");
        System.out.println("------------------");
        System.out.println("Username : " + getUsername());
        System.out.println("ID       : " + getId());
        System.out.println("Faculty  : " + getFaculty());
        System.out.println("Major    : " + getMajor());
        System.out.println("Email    : " + getEmail());
    }
}


