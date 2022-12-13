package com.example.myfix;

public class User {
    public String username;
    public String email;
    public String password;
    public String point;

    public User(){}

    public User(String username, String email, String password, String point){
        this.username = username;
        this.email = email;
        this.password = password;
        this.point = point;
    }


    public String getUsername(){
        return username; }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getPoint(){
        return point;
    }
}
