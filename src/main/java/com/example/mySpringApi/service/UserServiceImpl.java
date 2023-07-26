package com.example.mySpringApi.service;

import com.example.mySpringApi.exception.UserNotFoundException;
import com.example.mySpringApi.model.User;
import com.example.mySpringApi.repository.UserRepositoryI;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * UserServiceImpl is a service class that implements the UserServiceI interface.
 * It provides methods for retrieving, creating, updating and deleting User objects.
 *
 * This class is marked with the @Service annotation, which indicates that it's a Spring
 * service and a candidate for Spring's component scanning to detect and add to the application context.
 *
 * This service class uses UserRepositoryI for data access.
 *
 * TODO: Validate that the user making the request has the necessary permissions.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserServiceI {

    private UserRepositoryI userRepositoryI;

    private List<User> userList;

    /**
     * Constructs a new UserServiceImpl with a UserRepositoryI.
     *
     * @param userRepositoryI the repository that provides access to the user data store
     */
    @Autowired
    public UserServiceImpl(UserRepositoryI userRepositoryI) {
        this.userRepositoryI = userRepositoryI;
    }

    /**
     * Fetches a User by their ID from the UserRepository.
     *
     * This method interacts with the UserRepository to fetch a User based on the provided ID. The `findById` method
     * of the UserRepository returns an Optional<User>. In this case, we are using the `orElseThrow()` method to retrieve
     * the User object if it exists, or throw a UserNotFoundException if the User does not exist.
     *
     * @param id The ID of the User to be fetched.
     * @return The User object associated with the provided ID.
     * @throws UserNotFoundException If the User with the provided ID does not exist.
     */
    public User getUser(Integer id) {
        log.info("(getUser(Integer id) service method) Getting a user from the database via id");
        // TODO: Log an error message when a UserNotFoundException is thrown.
        return userRepositoryI.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    /**
     * Retrieves a User by name from the UserRepository. Returns the found User,
     * or throws a UserNotFoundException if no User with the provided name is found.
     *
     * @param name the name of the User to retrieve
     * @return The User object associated with the provided name.
     * @throws UserNotFoundException If the User with the provided name does not exist.
     *
     * TODO: Add handling for partial matches in user name search.
     * TODO: Consider whether multiple users could have the same name and how to handle such situations.
     */
    public User getUser(String name) {
        log.info("(getUser(String name) service method) Getting a user from the database via name");
        // TODO: Log an error message when a UserNotFoundException is thrown.
        return userRepositoryI.findByName(name).orElseThrow(() -> new UserNotFoundException());
    }


    /**
     * Creates a new User object in the database. It accepts a User object,
     * logs the action, then saves the User object in the database using the save() method
     * of the UserRepositoryI interface.
     *
     * @param user - A User object to be saved in the database.
     * @return the User object that was saved in the database. This will include any changes
     * made to the User object by the database, such as the automatically generated ID for
     * a new User.
     *
     * TODO: Consider implementing validation checks, such as ensuring that the name and email are not already in use.
     * TODO: Add error handling for save() operation.
     */
    public User createUser(User user) {
        log.warn("(createUser service method) Saving new user to the database");
        return userRepositoryI.save(user);
    }

    /**
     * Updates an existing User object in the database. It accepts a User object
     * and first checks if the User object exists in the database using the existsById() method
     * of the UserRepositoryI interface. If the User object does not exist, an EntityNotFoundException
     * is thrown.
     *
     * If the User object does exist, then the User object is updated in the database using the
     * save() method of the UserRepositoryI interface.
     *
     * @param user - A User object to be updated in the database. This User object should already
     * exist in the database and have a valid ID.
     * @return the User object that was updated in the database. This will include any changes
     * made to the User object by the database.
     * @throws EntityNotFoundException if the User object does not exist in the database.
     *
     * TODO: Consider implementing validation checks, such as ensuring that the name and email are not already in use.
     * TODO: Add error handling for save() operation.
     */
    public User updateUser(User user) {
        log.warn("(updateUser service method) Updated a user from the database");

        // ensures the user exists before updating
        if (!userRepositoryI.existsById(user.getId())) {
            throw new EntityNotFoundException("User not found with id " + user.getId());
        }
        return userRepositoryI.save(user);
    }

    /**
     * Deletes a user from the database. It uses the UserRepositoryI's deleteById method
     * to remove the user with the provided id from the database. If there is no user with the given
     * id in the database, it simply returns without throwing an exception. The handling of non-existent
     * users can be done where this service method is called, typically in the UserController.
     *
     * @param id The id of the user to delete.
     */
    public void deleteUser(int id) {
        log.warn("(deleteUser service method) Deleting user from the database");
        userRepositoryI.deleteById(id);
    }

    /**
     * Retrieves all User objects from the repository.
     *
     * @return a List of User objects. If no Users exist, returns an empty List.
     */
    @Override
    public List<User> getAllUsers() {
        log.info("(getAllUsers service method) Getting all users from the database");
        return userRepositoryI.findAll();
    }
}
