package com.example.mySpringApi.api.controller;

import com.example.mySpringApi.model.User;
import com.example.mySpringApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * UserController is a REST controller that exposes endpoints for interacting with User resources.
 * It routes incoming HTTP requests to appropriate handler methods.
 *
 * This class is annotated with @RestController, indicating it's a RESTful controller.
 * The @RequestMapping("/userAPI") annotation is used at the class level to map the URL prefix for all handler methods
 * in this class.
 *
 * The UserService is injected into this controller via constructor injection.
 *
 * Two handler methods are provided for fetching User resources:
 * 1. @GetMapping("/id/{id}") maps to a method which retrieves a User by their ID.
 * 2. @GetMapping("/name/{name}") maps to a method which retrieves a User by their name.
 *
 * If a User resource is found, it is returned as the response body.
 * If no User resource is found, null is returned.
 *
 * TODO: Test current CRUD operations with Postman
 * TODO: Align User model fields with client data with JSON structure in POST request
 */
@RestController
@RequestMapping("/userAPI")
public class UserController {

    private UserService userService;

    // Construct Injection
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * Retrieves a User by ID.
     * @param id the ID of the User to retrieve
     * @return the User object if found; null otherwise
     */
    @GetMapping("/id/{id}")
    public User getUser(@PathVariable int id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()) {
            return (User) user.get();
        }
        return null;
    }

    /**
     * Retrieves a User by name.
     * @param name the name of the User to retrieve
     * @return the User object if found; null otherwise
     */
    @GetMapping("/name/{name}")
    public User getUser(@PathVariable String name) {
        Optional<User> user = userService.getUser(name);
        if (user.isPresent()) {
            return (User) user.get();
        }
        return null;
    }

    /**
     * Endpoint to create a new user.
     *
     * This method is mapped to the "/createUser" endpoint and handles HTTP POST requests.
     * It receives a User object from the request body via the @RequestBody annotation.
     * This User object is then passed to the createUser method of the UserService, which
     * saves the user to the database. The User object saved in the database, including
     * any updates made by the database (like the generated ID for a new user), is returned
     * in the HTTP response body.
     *
     * @param user A User object contained in the request body.
     * @return The User object saved in the database, including any updates made by the database.
     */
    @PostMapping("/createUser")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    /**
     * Endpoint to update an existing user.
     *
     * This method is mapped to the "/updateUser" endpoint and handles HTTP PUT requests.
     * It receives a User object from the request body via the @RequestBody annotation. This
     * User object should have an ID that corresponds to an existing user in the database.
     * This User object is then passed to the updateUser method of the UserService, which
     * updates the user in the database. The updated User object, including any changes
     * made during the update, is returned in the HTTP response body.
     *
     * If a User with the specified ID does not exist in the database, the updateUser method
     * throws an EntityNotFoundException.
     *
     * @param user A User object that is included in the request body. This should include
     *             the ID of the user to be updated.
     * @return The updated User object, including any changes made during the update.
     */
    @PutMapping("/updateUser")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }
}
