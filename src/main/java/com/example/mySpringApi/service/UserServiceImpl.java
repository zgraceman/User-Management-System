package com.example.mySpringApi.service;

import com.example.mySpringApi.exception.InvalidUserInputException;
import com.example.mySpringApi.exception.UserAlreadyExistsException;
import com.example.mySpringApi.exception.UserNotFoundException;
import com.example.mySpringApi.model.User;
import com.example.mySpringApi.repository.UserRepositoryI;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        return userRepositoryI.findByName(name).orElseThrow(() -> new UserNotFoundException());
    }

    /**
     * This method is responsible for creating a new User object and saving it to the database.
     *
     * Initially, it checks the validity of the provided user details. If the details are found to be invalid, an InvalidUserInputException is thrown.
     * Then, it verifies whether a user with the same email already exists in the system. If such a user exists, it throws a UserAlreadyExistsException.
     * Finally, it attempts to save the new user to the database. In the event of a data integrity violation (for instance, due to a duplicate email), it catches the DataIntegrityViolationException and rethrows it as a RuntimeException.
     *
     * @param user - The User object containing details of the new user to be created. It should include all required information.
     * @return Returns the User object that was created and saved in the database.
     * @throws InvalidUserInputException - Thrown when the provided user details are not valid.
     * @throws UserAlreadyExistsException - Thrown when a user with the same email already exists in the system.
     * @throws RuntimeException - Thrown when there's a failure in saving the user to the database due to data integrity violations, such as a duplicate email.
     */
    @Transactional
    public User createUser(User user) {
        log.warn("(createUser service method) Saving new user to the database");

        if (!isValidUser(user)) {
            throw new InvalidUserInputException("The provided user details are invalid.");
        }

        // Check if a user with the same email exists
        Optional<User> existingUser = userRepositoryI.findByEmail(user.getEmail());

        if(existingUser.isPresent()){
            throw new UserAlreadyExistsException("A user with email " + user.getEmail() + " already exists.");
        }

        try {
            return userRepositoryI.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Could not save the user to the database", e);
        }
    }

    /**
     * This method updates a pre-existing User object in the database. The User object to be updated should already exist in the database, identified by a valid ID.
     *
     * It first checks whether the user exists in the database. If it doesn't, it throws a UserNotFoundException.
     * It then verifies the validity of the provided user details. If the details are invalid, it throws an InvalidUserInputException.
     * Subsequently, it checks whether there is an existing user with the same email (excluding the current user). If there is, it throws a UserAlreadyExistsException.
     * Finally, it tries to save the user object in the database. If the save operation violates data integrity (for example, due to a duplicate email), it catches the DataIntegrityViolationException and wraps it into a RuntimeException.
     *
     * @param user - The User object to be updated. This object should have a valid ID and be present in the database.
     * @return Returns the updated User object, including any changes made by the database.
     * @throws UserNotFoundException Thrown when the User object does not exist in the database.
     * @throws InvalidUserInputException Thrown when the provided user details are not valid.
     * @throws UserAlreadyExistsException Thrown when a user with the same email already exists in the system (excluding the current user).
     * @throws RuntimeException Thrown when there is a failure in updating the user in the database due to data integrity violations (e.g., duplicate email).
     */
    @Transactional
    public User updateUser(User user) {

        if (!userRepositoryI.existsById(user.getId())) {
            throw new UserNotFoundException("User with id " + user.getId() + " does not exist.");
        }

        if (!isValidUser(user)) {
            throw new InvalidUserInputException("The provided user details are invalid.");
        }

        Optional<User> userWithSameEmail = userRepositoryI.findByEmail(user.getEmail());

        if (userWithSameEmail.isPresent() && userWithSameEmail.get().getId() != user.getId()) {
            throw new UserAlreadyExistsException("A user with email " + user.getEmail() + " already exists.");
        }

        try {
            return userRepositoryI.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Could not update the user in the database", e);
        }
    }

    /**
     * Deletes a user from the database. It uses the UserRepositoryI's deleteById method
     * to remove the user with the provided id from the database. If there is no user with the given
     * id in the database, it throws a UserNotFoundException. This exception should be handled where
     * this service method is called, typically in the UserController.
     *
     * @param id The id of the user to delete.
     * @throws UserNotFoundException If the User with the provided id does not exist.
     */
    public void deleteUser(int id) {
        log.warn("(deleteUser service method) Attempting to delete a user from the database");

        // Check if user exists before trying to delete
        if (!userRepositoryI.existsById(id)) {
            throw new UserNotFoundException("User not found with id " + id);
        }

        userRepositoryI.deleteById(id);
        log.warn("(deleteUser service method) User with id " + id + " deleted successfully");
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

    /**
     * Private helper method to validate a User object.
     * This method checks whether the provided User object is valid based on the business rules.
     * It checks if the user's name and email are not null, if the name length is acceptable,
     * if the email is in a valid format and if they are not empty strings.
     *
     * @param user The User object to be validated.
     * @return boolean indicating whether the User object is valid (true) or not (false).
     */
    private boolean isValidUser(User user) {
        if (user.getName() == null || user.getEmail() == null) {
            return false;
        }

        String trimmedName = user.getName().trim();
        String trimmedEmail = user.getEmail().trim();

        // Check for empty strings
        if (trimmedName.isEmpty() || trimmedEmail.isEmpty()) {
            return false;
        }

        // Check for name length
        if (trimmedName.length() < 3 || trimmedName.length() > 50) {
            return false;
        }

        // Check for valid email format
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(trimmedEmail);

        return matcher.matches();
    }
}
