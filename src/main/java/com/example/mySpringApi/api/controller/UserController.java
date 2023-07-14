package com.example.mySpringApi.api.controller;

import com.example.mySpringApi.api.model.User;
import com.example.mySpringApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    private UserService userService;

    // Construct Injection
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Example: localhost:8080/userById?id=1 will return the User object associated with id 1
    @GetMapping("/userById")
    public User getUser(@RequestParam Integer id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            return (User) user.get();
        }
        return null;
    }

    // Example: localhost:8080/userByName?name=Zach will return the User object associated with the name Zach
    @GetMapping("/userByName")
    public User getUser(@RequestParam String name) {
        Optional<User> user = userService.getUser(name);
        if (user.isPresent()) {
            return (User) user.get();
        }
        return null;
    }
}
