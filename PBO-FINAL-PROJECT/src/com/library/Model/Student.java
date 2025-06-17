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

    @Override
    public void login (){
        super.login();
    }

    @Override
    public void displayinfo ( ){
        System.out.println("Username : " + this.getUsername());
        System.out.println("ID : " + this.getId());
        System.out.println("Faculty : " + this.getFaculty());
        System.out.println("Major : " + this.getMajor());
        System.out.println("Email : " + this.getEmail());
        System.out.println("---------------------------------------");
        System.out.println("You are a student");
        System.out.println("You can borrow books from the library");
        System.out.println("You can request a book return");
    }

}

