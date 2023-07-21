package com.example.mySpringApi.service;

import com.example.mySpringApi.model.User;
import com.example.mySpringApi.repository.UserRepositoryI;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 */
@Service
@Slf4j
public class UserServiceImpl implements UserServiceI {

    private UserRepositoryI userRepositoryI;

    private List<User> userList;

    /**
     * Constructs a new UserService with a UserRepository.
     *
     * @param userRepositoryI the repository that provides access to the user data store
     */
    @Autowired
    public UserServiceImpl(UserRepositoryI userRepositoryI) {
        this.userRepositoryI = userRepositoryI;
    }

    /**
     * Retrieves a User by ID from the list. Returns an Optional<User> that contains
     * the found User, or is empty if no User with the provided ID is found.
     *
     * @param id the ID of the User to retrieve
     * @return an Optional<User> containing the found User, or empty if not found
     *
     */
    public Optional<User> getUser(Integer id) {
        log.info("getting a user from the database via id");

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
        log.info("getting a user from the database via name");

        return userRepositoryI.findByName(name);
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
     * TODO: Add creation-specific logic or validation
     */
    public User createUser(User user) {
        log.warn("Saving new user to the database");
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
     */
    public User updateUser(User user) {
        log.warn("Updated a user from the database");

        // ensures the user exists before updating
        if (!userRepositoryI.existsById(user.getId())) {
            throw new EntityNotFoundException("User not found with id " + user.getId());
        }
        return userRepositoryI.save(user);
    }

    /**
     * Deletes a user from the database.
     *
     * This method uses the UserRepositoryI's deleteById method to remove the user
     * with the provided id from the database. If there is no user with the given
     * id in the database, the JpaRepository's deleteById method throws an
     * EmptyResultDataAccessException. The handling of this exception can be done
     * where this service method is called, typically in the UserController.
     *
     * @param id The id of the user to delete.
     */
    public void deleteUser(int id) {
        log.warn("Deleting user from the database");
        userRepositoryI.deleteById(id);
    }
}
