package com.advse.universitybazaar.bean;

/**
 * Created by shahsk0901 on 2/14/18.
 */

public class Student {
    private String mavID;
    private String name;
    private String password;

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
}
