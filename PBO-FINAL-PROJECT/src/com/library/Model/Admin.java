package com.library.Model;

public class Admin extends User{
    public Admin(String username, String id){
        super(username,id);
    }
    @Override
    public void login (){
        super.login();
    }
    @Override
    public void displayinfo ( ){
        System.out.println("Username : " + this.getUsername());
        System.out.println("ID : " + this.getId());
        System.out.println("---------------------------------------");
    }
}
