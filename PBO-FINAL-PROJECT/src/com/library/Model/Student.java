package com.library.Model;

public class Student extends User{
    private String major;
    private String email;

    public Student(String username, String id, String major,String email){
        super(username, id);
        this.major = major;
        this.email = email;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
