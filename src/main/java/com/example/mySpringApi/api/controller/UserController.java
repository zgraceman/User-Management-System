package com.example.mySpringApi.api.controller;

import com.example.mySpringApi.api.response.ResponseHandler;
import com.example.mySpringApi.model.User;
import com.example.mySpringApi.service.UserServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * UserController is a REST controller that exposes endpoints for interacting with User resources.
 * It routes incoming HTTP requests to appropriate handler methods.
 *
 * This class is annotated with @RestController, indicating it's a RESTful controller.
 * The @RequestMapping("/userAPI") annotation is used at the class level to map the URL prefix for all handler methods
 * in this class.
 *
 * The UserServiceI is injected into this controller via constructor injection.
 *
 * Two handler methods are provided for fetching User resources:
 * 1. @GetMapping("/id/{id}") maps to a method which retrieves a User by their ID.
 * 2. @GetMapping("/name/{name}") maps to a method which retrieves a User by their name.
 *
 * If a User resource is found, it is returned as the response body.
 * If no User resource is found, null is returned.
 *
 * TODO: Align User model fields with client data with JSON structure in POST request
 */
@RestController
@RequestMapping("/userAPI")
@Slf4j
public class UserController {

    private UserServiceI userServiceI;

    // Construct Injection
    @Autowired
    public UserController(UserServiceI userServiceI) {
        this.userServiceI = userServiceI;
    }

    /**
     * Retrieves a User by ID.
     * @param id the ID of the User to retrieve
     * @return a ResponseEntity that includes the User object if found, along with a message and HTTP status code.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getUser(@PathVariable int id) {
        User user = userServiceI.getUser(id);
        return ResponseHandler.generateResponse("User fetched successfully", HttpStatus.OK, user);
    }

    /**
     * Retrieves a User by name.
     * @param name the name of the User to retrieve
     * @return a ResponseEntity that includes the User object if found, along with a message and HTTP status code.
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<Object> getUser(@PathVariable String name) {
        log.info("I am in the getUser /name/{name} controller method");
        User user = userServiceI.getUser(name);
        return ResponseHandler.generateResponse("User fetched successfully", HttpStatus.OK, user);
    }

    /**
     * Fetches all Users.
     *
     * This method handles GET requests at the "/userAPI" endpoint. It returns a list of all
     * users from the data store or an appropriate error response if no users exist.
     *
     * @return a ResponseEntity that includes the list of all User objects or an appropriate error response.
     */
    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        log.info("I am in the getAllUsers controller method");
        List<User> users = userServiceI.getAllUsers();
        return ResponseHandler.generateResponse("All users fetched", HttpStatus.OK, users);
    }

    /**
     * Endpoint to create a new user.
     *
     * This method is mapped to the "/createUser" endpoint and handles HTTP POST requests.
     * It receives a User object from the request body via the @RequestBody annotation.
     * This User object is then passed to the createUser method of the UserService, which
     * saves the user to the database. The User object saved in the database, including
     * any updates made by the database (like the generated ID for a new user), is returned
     * in a structured response along with a message and HTTP status code.
     *
     * @param user A User object contained in the request body.
     * @return a ResponseEntity that includes the User object saved in the database, a message, and an HTTP status code.
     */
    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        log.warn("I am in the createUser controller method");
        User createdUser = userServiceI.createUser(user);
        return ResponseHandler.generateResponse("User successfully created", HttpStatus.CREATED, createdUser);
    }

    /**
     * Endpoint to update an existing user.
     *
     * This method is mapped to the "/updateUser" endpoint and handles HTTP PUT requests.
     * It receives a User object from the request body via the @RequestBody annotation. This
     * User object should have an ID that corresponds to an existing user in the database.
     * This User object is then passed to the updateUser method of the UserService, which
     * updates the user in the database. The updated User object, including any changes
     * made during the update, is returned in a structured response along with a message and HTTP status code.
     *
     * If a User with the specified ID does not exist in the database, the updateUser method
     * throws an EntityNotFoundException.
     *
     * @param user A User object that is included in the request body. This should include
     *             the ID of the user to be updated.
     * @return a ResponseEntity that includes the updated User object, a message, and an HTTP status code.
     */
    @PutMapping("/updateUser")
    public ResponseEntity<Object> updateUser(@RequestBody User user) {
        log.warn("I am in the updateUser controller method");
        User updatedUser = userServiceI.updateUser(user);
        return ResponseHandler.generateResponse("User updated successfully", HttpStatus.OK, updatedUser);
    }

    /**
     * Endpoint to delete an existing user.
     *
     * This method is mapped to the "/deleteUser/{id}" endpoint and handles HTTP DELETE
     * requests. It takes an id path variable, passed by the @PathVariable annotation.
     * This id is then passed to the deleteUser method of the UserService, which deletes
     * the user with the corresponding id from the database.
     *
     * The method returns a structured response with a message and HTTP 200 OK status in the response to indicate that
     * the user was successfully deleted.
     *
     * If the UserService's deleteUser method throws an EmptyResultDataAccessException
     * (because there is no user with the given id in the database), this method
     * should catch that exception and return an appropriate error response.
     *
     * @param id The id of the user to delete, included in the path of the request.
     * @return A ResponseEntity with a message, HTTP status code, and no data.
     */
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        log.warn("I am in the deleteUser /deleteUser/{id} controller method");
        // TODO: Return an appropriate HTTP error status (like 404 NOT FOUND)
        // TODO: if the UserService's deleteUser method throws an EmptyResultDataAccessException.
        userServiceI.deleteUser(id);
        return ResponseHandler.generateResponse("User deleted successfully", HttpStatus.OK, null);
    }
}