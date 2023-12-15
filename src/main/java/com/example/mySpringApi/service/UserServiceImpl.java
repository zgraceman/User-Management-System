package com.example.mySpringApi.service;

import com.example.mySpringApi.exception.InvalidUserInputException;
import com.example.mySpringApi.exception.UserAlreadyExistsException;
import com.example.mySpringApi.exception.UserNotFoundException;
import com.example.mySpringApi.model.Role;
import com.example.mySpringApi.model.User;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UserServiceImpl is a service class that implements the UserService interface.
 * It provides methods for retrieving, creating, updating and deleting User objects.
 * <p>
 * This class is marked with the @Service annotation, which indicates that it's a Spring
 * service and a candidate for Spring's component scanning to detect and add to the application context.
 * <p>
 * This service class uses UserRepository for data access.
 * <p>
 * TODO: Validate that the user making the request has the necessary permissions.
 */
@Service
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleServiceImpl roleServiceImpl;

    private List<User> userList;

    /**
     * Constructs a new UserServiceImpl with a UserRepository.
     *
     * @param userRepository the repository that provides access to the user data store
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleServiceImpl roleServiceImpl) {
        this.userRepository = userRepository;
        this.roleServiceImpl = roleServiceImpl;
    }

    /**
     * {@inheritDoc}
     * <p>
     * In this implementation, the method queries the UserRepository using the provided ID.
     * It uses the `findById` method of the UserRepository, which returns an Optional.
     * If the Optional is empty (indicating that no user was found with the given ID),
     * a UserNotFoundException is thrown, ensuring that the caller is informed of the
     * absence of a user with the requested ID.
     */
    @Override
    public User getUser(Integer id) {
        System.out.println("DEBUG: I am in the getUserByID service method");
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    /**
     * {@inheritDoc}
     *
     * This implementation retrieves the User entity by its email using the `findByEmail` method
     * of the UserRepository. It returns an Optional<User>, and if no user is found with the
     * given email (i.e., the Optional is empty), a UserNotFoundException is thrown.
     * This ensures that the caller is made aware of the non-existence of a user with the given email.
     * <p>
     * TODO: Add handling for partial matches in user email search.
     */
    @Override
    public User getUser(String email) {
        System.out.println("DEBUG: I am in the getUserByEmail service method");
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
    }

    /**
     * {@inheritDoc}
     *
     * This implementation leverages the `findAll` method of the UserRepository to fetch
     * all User entities stored in the database. It returns a complete list of users,
     * providing a broad overview of all users within the system. If there are no users,
     * an empty list is returned.
     */
    @Override
    public List<User> getAllUsers() {
        System.out.println("DEBUG: I am in the getAllUsers service method");
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     *
     * This implementation of createUser first verifies the user's details for validity
     * and uniqueness of email in the database. It throws specific exceptions if the input
     * is invalid or if a user with the same email already exists. In case of successful validation,
     * the user is persisted in the database. Any data integrity violations during the persistence
     * process are caught and rethrown as a RuntimeException.
     */
    @Override
    @Transactional
    public User createUser(User user) {
        System.out.println("DEBUG: I am in the createUser service method");

        if (!isValidUser(user)) {
            throw new InvalidUserInputException("The provided user details are invalid.");
        }

        // Check if a user with the same email exists
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if(existingUser.isPresent()){
            throw new UserAlreadyExistsException("A user with email " + user.getEmail() + " already exists.");
        }

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) { // consider testing
            throw new RuntimeException("Could not save the user to the database", e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * This implementation first checks if the user with the given ID exists in the database.
     * If not, a UserNotFoundException is thrown. It then validates the user details,
     * throwing an InvalidUserInputException for invalid data. It also ensures that no other
     * existing user has the same email address as the one being updated, except for the user
     * being updated itself, to avoid conflicts. After these validations, it attempts to save
     * the updated user data to the database, throwing a RuntimeException for any data integrity
     * violations during this process.
     */
    @Override
    @Transactional
    public User updateUser(User user) {
        System.out.println("DEBUG: I am in the updateUser service method");

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

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) { // consider testing
            throw new RuntimeException("Could not update the user in the database", e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * This implementation first verifies the existence of the User in the database using the provided ID.
     * If the user is not found, a UserNotFoundException is thrown. Otherwise, the method proceeds
     * to delete the user and logs a warning message indicating successful deletion.
     */
    @Override
    public void deleteUser(int id) {
        System.out.println("DEBUG: I am in the deleteUser service method");

        // Check if user exists before trying to delete
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id " + id);
        }

        userRepository.deleteById(id);
        log.warn("(deleteUser service method) User with id " + id + " deleted successfully");
    }

    // Helper Methods

    /**
     * {@inheritDoc}
     *
     * This implementation checks various aspects of the User object:
     * - Ensures the user's name and email are not null.
     * - Verifies the name is not an empty string and is within a length range of 2 to 50 characters.
     * - Checks the email format against a standard email regex pattern.
     */
    @Override
    public boolean isValidUser(User user) {
        System.out.println("DEBUG: I am in the isValidUser service method");

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

    /**
     * {@inheritDoc}
     *
     * This implementation first checks if a user with the specified email exists in the database.
     * If not, it creates a new User entity with the provided name, email, age, and password.
     * The user is then assigned a role based on the roleName parameter. The role is retrieved
     * using the RoleServiceImpl. The newly created user is then saved to the UserRepository.
     */
    @Override
    @Transactional
    public void createDefaultUserIfNotFound(String name, String email, int age, String password, String roleName) {
        System.out.println("DEBUG: I am in the createDefaultUserIfNotFound service method");

        Optional<User> existingUser = userRepository.findByEmail("defaultUser@example.com");
        if (existingUser.isEmpty()) {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            user.setPassword(password);
            Set<Role> roles = roleServiceImpl.findRolesByNames(Collections.singleton(roleName)); // Example method
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}
