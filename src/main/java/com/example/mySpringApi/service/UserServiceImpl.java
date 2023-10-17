package com.example.mySpringApi.service;

import com.example.mySpringApi.exception.InvalidUserInputException;
import com.example.mySpringApi.exception.UserAlreadyExistsException;
import com.example.mySpringApi.exception.UserNotFoundException;
import com.example.mySpringApi.model.User;
import com.example.mySpringApi.model.dto.UserDTO;
import com.example.mySpringApi.repository.UserRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UserServiceImpl is a service class that implements the UserService interface.
 * It provides methods for retrieving, creating, updating and deleting User objects.
 *
 * This class is marked with the @Service annotation, which indicates that it's a Spring
 * service and a candidate for Spring's component scanning to detect and add to the application context.
 *
 * This service class uses UserRepository for data access.
 *
 * TODO: Validate that the user making the request has the necessary permissions.
 */
@Service
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private List<User> userList;

    /**
     * Constructs a new UserServiceImpl with a UserRepository.
     *
     * @param userRepository the repository that provides access to the user data store
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Fetches a User by their ID from the UserRepository.
     *
     * This method interacts with the UserRepository to fetch a User based on the provided ID. The `findById` method
     * of the UserRepository returns an {@code Optional<User>}. In this case, we are using the `orElseThrow()` method to retrieve
     * the User object if it exists, or throw a UserNotFoundException if the User does not exist.
     *
     * @param id The ID of the User to be fetched.
     * @return The User object associated with the provided ID.
     * @throws UserNotFoundException If the User with the provided ID does not exist.
     */
    public User getUser(Integer id) {
        log.info("(getUser(Integer id) service method) Getting a user from the database via id");
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    /**
     * Retrieves a User by email from the UserRepository. Returns the found User,
     * or throws a UserNotFoundException if no User with the provided email is found.
     *
     * @param email the email of the User to retrieve
     * @return The User object associated with the provided email.
     * @throws UserNotFoundException If the User with the provided email does not exist.
     *
     * TODO: Add handling for partial matches in user email search.
     */
    public User getUser(String email) {
        log.info("(getUser(String name) service method) Getting a user from the database via name");
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
    }

    /**
     * Retrieves all User objects from the repository.
     *
     * @return a List of User objects. If no Users exist, returns an empty List.
     */
    @Override
    public List<User> getAllUsers() {
        log.info("(getAllUsers service method) Getting all users from the database");
        return userRepository.findAll();
    }

    /**
     * Creates a new User entity in the database.
     *
     * This method checks the validity of the user data before attempting to persist it.
     * If the user data is invalid, an InvalidUserInputException is thrown.
     * The method also checks for a pre-existing user with the same email.
     * If such a user is found, a UserAlreadyExistsException is thrown.
     *
     * Encrypts the user's password using BCrypt
     *
     * The @Transactional annotation ensures that the user creation process is atomic,
     * thus any failures during the process result in a rollback of the transaction.
     *
     * @param user The User object with data for creating a new user. Should include all required information.
     * @return The created User object saved in the database.
     * @throws InvalidUserInputException If the provided user details are invalid.
     * @throws UserAlreadyExistsException If a user with the same email already exists.
     * @throws RuntimeException If there's a failure in saving the user due to data integrity violation.
     */
    @Transactional
    public User createUser(User user) {
        log.warn("(createUser service method) Saving new user to the database");

        if (!isValidUser(user)) {
            throw new InvalidUserInputException("The provided user details are invalid.");
        }

        // Check if a user with the same email exists
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if(existingUser.isPresent()){
            throw new UserAlreadyExistsException("A user with email " + user.getEmail() + " already exists.");
        }

        // Encrypt the password before saving
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) { // consider testing
            throw new RuntimeException("Could not save the user to the database", e);
        }
    }

    /**
     * Updates a pre-existing User entity in the database.
     *
     * This method checks for the existence of the user in the database based on the user ID.
     * If the user does not exist, a UserNotFoundException is thrown.
     *
     * The method also validates the user data before attempting to persist the changes.
     * If the data is invalid, an InvalidUserInputException is thrown.
     * It also checks if there's an existing user with the same email (excluding the current user).
     * If there is, a UserAlreadyExistsException is thrown.
     *
     * Encrypts the user's password using BCryptgit 
     *
     * The @Transactional annotation ensures that the user update process is atomic,
     * thus any failures during the process result in a rollback of the transaction.
     *
     * @param user The User object to be updated. Should include a valid ID.
     * @return The updated User object, including any changes persisted in the database.
     * @throws UserNotFoundException If the User object to be updated does not exist in the database.
     * @throws InvalidUserInputException If the provided user details are invalid.
     * @throws UserAlreadyExistsException If a user with the same email already exists.
     * @throws RuntimeException If there's a failure in updating the user due to data integrity violation.
     */
    @Transactional
    public User updateUser(User user) {

        if (!userRepository.existsById(user.getId())) {
            throw new UserNotFoundException("User with id " + user.getId() + " does not exist.");
        }

        if (!isValidUser(user)) {
            throw new InvalidUserInputException("The provided user details are invalid.");
        }

        Optional<User> userWithSameEmail = userRepository.findByEmail(user.getEmail());

        if (userWithSameEmail.isPresent() && userWithSameEmail.get().getId() != user.getId()) {
            throw new UserAlreadyExistsException("A user with email " + user.getEmail() + " already exists.");
        }

        // Encrypt the password before saving
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) { // consider testing
            throw new RuntimeException("Could not update the user in the database", e);
        }
    }

    /**
     * Deletes a user from the database. It uses the UserRepository's deleteById method
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
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id " + id);
        }

        userRepository.deleteById(id);
        log.warn("(deleteUser service method) User with id " + id + " deleted successfully");
    }

    // Helper Methods

    /**
     * Private helper method to validate a User object.
     * This method checks whether the provided User object is valid based on the business rules.
     * It checks if the user's name and email are not null, if the name length is acceptable,
     * if the email is in a valid format and if they are not empty strings.
     *
     * @param user The User object to be validated.
     * @return boolean indicating whether the User object is valid (true) or not (false).
     */
    public boolean isValidUser(User user) {
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
        if (trimmedName.length() < 2 || trimmedName.length() > 50) {
            return false;
        }

        // Check for valid email format
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(trimmedEmail);

        return matcher.matches();
    }
}
