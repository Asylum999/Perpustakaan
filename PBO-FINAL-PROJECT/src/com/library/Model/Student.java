package com.library.Model;

public class Student extends User{
    private String faculty;
    private String major;
    private String email;

    public Student(String username, String id, String faculty, String major,String email){
        super(username, id);
        this.faculty = faculty;
        this.major = major;
        this.email = email;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getMajor() {
        return major;
    }

    public String getEmail() {
        return email;
    }
}
