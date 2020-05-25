package com.loststars.tmallboot.pojo;

public class Role {

    public static final String ADMIN = "admin";
    
    public static final String USER = "user";
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
