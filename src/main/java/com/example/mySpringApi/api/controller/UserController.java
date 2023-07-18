package com.example.mySpringApi.api.controller;

import com.example.mySpringApi.model.User;
import com.example.mySpringApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/userAPI")
public class UserController {

    private UserService userService;

    // Construct Injection
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/id/{id}")
    public User getUser(@PathVariable int id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            return (User) user.get();
        }
        return null;
    }

    @GetMapping("/name/{name}")
    public User getUser(@PathVariable String name) {
        Optional<User> user = userService.getUser(name);
        if (user.isPresent()) {
            return (User) user.get();
        }
        return null;
    }

    /**
     * Endpoint to add a new user.
     *
     * This method is mapped to the "/addUser" endpoint and handles HTTP POST requests.
     * It takes a User object from the request body, passed by the @RequestBody
     * annotation. This User object is then passed to the saveUser method of the
     * UserService, which saves the user to the database. The saved User object,
     * including any updates made by the database (such as the generated ID for
     * a new user), is then returned in the HTTP response body.
     *
     * @param user A User object that is included in the request body.
     * @return The saved User object, including any updates made by the database.
     */
    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
