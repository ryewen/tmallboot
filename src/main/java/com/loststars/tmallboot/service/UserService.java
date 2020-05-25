package com.loststars.tmallboot.service;

import java.util.List;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.loststars.tmallboot.dao.RoleDAO;
import com.loststars.tmallboot.dao.UserDAO;
import com.loststars.tmallboot.pojo.User;
import com.loststars.tmallboot.util.PageNavigator;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private RoleDAO roleDAO;
    
    public PageNavigator<User> listUsers(int start, int size, int navigatePages) {
        PageHelper.startPage(start, size);
        List<User> users = userDAO.listUsers();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        PageNavigator<User> page = new PageNavigator<>(pageInfo, users, navigatePages);
        return page;
    }
    
    public boolean userExists(User user) {
        if (userDAO.getUserByName(user.getName()) != null)
            return true;
        else
            return false;
    }
    
    @Transactional
    public void addUser(User user, String role) {
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String algorithmName = "md5";
        String encodedPassword = new SimpleHash(algorithmName, user.getPassword(), salt, times).toString();
        user.setSalt(salt);
        user.setPassword(encodedPassword);
        userDAO.addUser(user);
        roleDAO.addUserRole(user.getId(), role);
    }
    
    public User getUser(String name) {
        return userDAO.getUserByName(name);
    }
}
