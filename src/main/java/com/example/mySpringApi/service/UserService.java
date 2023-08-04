package com.example.mySpringApi.service;

import com.example.mySpringApi.model.User;

import java.util.List;

/**
 * UserService is the interface for the user service, defining the contract for the user service
 * to be implemented by any concrete class.
 *
 * The methods include basic CRUD operations for User entities, as well as retrieval
 * of User entities by ID and by name.
 *
 * This interface should be implemented by a class that interacts with the UserRepository
 * to perform these operations and handle any related business logic.
 */
public interface UserService {

    /**
     * Retrieves a User by ID from the repository.
     *
     * @param id The ID of the User to retrieve.
     * @return The User object associated with the provided ID.
     */
    User getUser(Integer id);

    /**
     * Retrieves a User by name from the repository.
     *
     * @param name the name of the User to retrieve
     * @return The User object associated with the provided name.
     */
    User getUser(String name);

    /**
     * Creates a new User object in the repository.
     *
     * @param user the User object to create
     * @return the created User object
     */
    User createUser(User user);

    /**
     * Updates an existing User object in the repository.
     *
     * @param user the User object to update
     * @return the updated User object
     */
    User updateUser(User user);

    /**
     * Deletes a User from the repository.
     *
     * @param id the ID of the User to delete
     */
    void deleteUser(int id);

    /**
     * Fetches all User objects from the data store.
     *
     * @return a List of all User objects. Returns an empty list if no Users are found.
     */
    List<User> getAllUsers();
}
