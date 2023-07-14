package com.example.mySpringApi.service;

import com.example.mySpringApi.model.User;
import com.example.mySpringApi.repository.UserRepositoryI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * UserService is a service class that provides methods for retrieving User objects
 * from a predefined list.
 *
 * This class is marked with the @Service annotation, making it a candidate
 * for Spring's component scanning to detect and add to the application context.
 *
 * The list of User objects is initialized in the constructor with some dummy data.
 *
 * TODO: Integrate with a database or other persistent data store instead of using hardcoded data.
 * TODO: Add methods to support other operations such as updating and deleting users.
 */
@Service
@Slf4j
public class UserService {

    private UserRepositoryI userRepositoryI;

    private List<User> userList;

    /**
     * Constructs a new UserService with a UserRepository.
     *
     * @param userRepositoryI the repository that provides access to the user data store
     */
    @Autowired
    public UserService(UserRepositoryI userRepositoryI) {
        this.userRepositoryI = userRepositoryI;
    }

    /**
     * Constructs a new UserService, initializes and populates the userList.
     *
     * TODO: Replace the hard-coded user data with data fetched from a persistent data store.
     */
    /*
    public UserService() {

        userList = new ArrayList<>();

        User user1 = new User(1, "Zach", 23, "zach@gmail.com");
        User user2 = new User(2, "Don", 33, "don@gmail.com");
        User user3 = new User(3, "Michelle", 20, "michelle@gmail.com");
        User user4 = new User(4, "Janet", 53, "janet@gmail.com");
        User user5 = new User(5, "Peter", 46, "peter@gmail.com");

        userList.addAll(Arrays.asList(user1, user2, user3, user4, user5));
    } */

    /**
     * Retrieves a User by ID from the list. Returns an Optional<User> that contains
     * the found User, or is empty if no User with the provided ID is found.
     *
     * @param id the ID of the User to retrieve
     * @return an Optional<User> containing the found User, or empty if not found
     *
     * TODO: Replace linear search with a more efficient search method when the user list grows large.
     */
    public Optional<User> getUser(Integer id) {

        log.info("getUser Integer id Method");

        return userRepositoryI.findById(id);
    }

    /**
     * Retrieves a User by name from the list. Returns an Optional<User> that contains
     * the found User, or is empty if no User with the provided name is found.
     *
     * @param name the name of the User to retrieve
     * @return an Optional<User> containing the found User, or empty if not found
     *
     * TODO: Add handling for case sensitivity and partial matches in user name search.
     * TODO: Consider whether multiple users could have the same name and how to handle such situations.
     */
    public Optional<User> getUser(String name) {

        log.info("getUser String name method");

        return userRepositoryI.findByName(name);
    }
}
