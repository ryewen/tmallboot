package com.loststars.tmallboot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loststars.tmallboot.pojo.User;
import com.loststars.tmallboot.service.UserService;
import com.loststars.tmallboot.util.PageNavigator;

@RestController
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public PageNavigator<User> listUsers(@RequestParam(value = "start", defaultValue = "1") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        return userService.listUsers(start, size, 5);
    }
}
