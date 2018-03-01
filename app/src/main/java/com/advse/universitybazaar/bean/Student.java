package com.advse.universitybazaar.bean;

/**
 * Created by shahsk0901 on 2/14/18.
 */

public class Student {
    private String mavID;
    private String name;
    private String password;
    private String email;

    public Student() {

    }

    public Student(String name){
        this.name = name;
    }

    public Student(String name, String mavID){
        this.name = name;
        this.mavID = mavID;
    }

    public Student(String id, String name, String pw, String email){
        this.mavID = id;
        this.name = name;
        this.password = pw;
        this.email =email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMavID() {
        return mavID;
    }

    public void setMavID(String mavID) {
        this.mavID = mavID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
