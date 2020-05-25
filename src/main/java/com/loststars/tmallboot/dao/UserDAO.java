package com.loststars.tmallboot.dao;

import java.util.List;

import com.loststars.tmallboot.pojo.User;

public interface UserDAO {

    public List<User> listUsers();
    
    public User getUserByName(String name);
    
    public void addUser(User user);
}
