package com.loststars.tmallboot.pojo;

import java.util.List;

public class User {

    private int id;
    
    private String name;
    
    private String password;
    
    private String salt;
    
    private String anonymousName;
    
    private List<Role> roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAnonymousName() {
        if (anonymousName != null) return anonymousName;
        if (name == null) return null;
        if (name.length() == 0) {
            anonymousName = "*****";
        } else {
            anonymousName = name.substring(0, 1) + "****";
        }
        return anonymousName;
    }

    public void setAnonymousName(String anonymousName) {
        this.anonymousName = anonymousName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
