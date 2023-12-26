package com.example.mySpringApi.service;

import com.example.mySpringApi.exception.InvalidUserInputException;
import com.example.mySpringApi.exception.UserAlreadyExistsException;
import com.example.mySpringApi.exception.UserNotFoundException;
import com.example.mySpringApi.model.User;
import com.example.mySpringApi.model.dto.UserDTO;
import com.example.mySpringApi.model.dto.UserResponseDTO;

import java.util.List;

/**
 * UserService is the interface for the user service, defining the contract for the user service
 * to be implemented by any concrete class.
 * <p>
 * The methods include basic CRUD operations for User entities, as well as retrieval
 * of User entities by ID and by name.
 * <p>
 * This interface should be implemented by a class that interacts with the UserRepository
 * to perform these operations and handle any related business logic.
 */
public interface UserService {

    /**
     * Retrieves a user by their unique identifier.
     * <p>
     * This method is intended to find and return a User object based on the provided ID.
     * It is a key method in user management operations, allowing for the retrieval of specific
     * user details.
     *
     * @param id The unique identifier of the user to retrieve.
     * @return The User object associated with the provided ID.
     * @throws UserNotFoundException If no user is found with the provided ID.
     */
    User getUser(Integer id);

    /**
     * Retrieves a user by their email address.
     *
     * <p>This method is designed to fetch a User object based on a provided email address.
     * It is an essential function in user management, especially when identifying users
     * by their unique email.</p>
     *
     * @param email The email address of the user to retrieve.
     * @return The User object associated with the provided email address.
     * @throws UserNotFoundException If no user is found with the provided email address.
     */
    User getUser(String email);

    /**
     * Retrieves all users from the repository.
     * <p>
     * This method fetches a list of all User objects currently stored in the data repository.
     * It's a key method for obtaining a comprehensive view of all registered users.
     *
     * @return A list of User objects. If no users are found, it returns an empty list.
     */
    List<User> getAllUsers();

    /**
     * Creates a new user and saves it to the repository.
     * <p>
     * This method is designed to take a User object, validate its contents, and then persist it
     * in the database. It ensures that user details meet business requirements and that
     * the email is not already used by another user.
     *
     * @param user The User object to be created.
     * @return The persisted User object.
     * @throws InvalidUserInputException If the user details fail validation checks.
     * @throws UserAlreadyExistsException If a user with the same email already exists in the database.
     * @throws RuntimeException If there is an error during the persistence process.
     */
    User createUser(User user);

    /**
     * Updates an existing User in the repository.
     * <p>
     * This method is responsible for updating the information of an already existing User. It takes a User object
     * with updated data, including a valid ID, and applies these changes in the data store.
     *
     * @param user The User object with updated information.
     * @return The updated User object.
     * @throws UserNotFoundException If a user with the provided ID does not exist.
     * @throws InvalidUserInputException If the updated user details are invalid.
     * @throws UserAlreadyExistsException If a user with the updated email already exists, excluding the current user.
     * @throws RuntimeException If there is an error during the persistence process.
     */
    User updateUser(User user);

    /**
     * Deletes a User from the repository.
     *
     * This method removes a User identified by the provided ID from the data store.
     * It is important to ensure that the user exists before attempting deletion to prevent errors.
     *
     * @param id The ID of the User to be deleted.
     * @throws UserNotFoundException If no User with the provided ID exists in the repository.
     */
    void deleteUser(int id);

    /**
     * Validates a User object based on specific business criteria.
     * <p>
     * Implementations of this method should check the user's name and email for null values,
     * ensure the name is not an empty string and is within acceptable length bounds,
     * and verify that the email is in a valid format.
     *
     * @param user The User object to be validated.
     * @return true if the User object meets the validation criteria; otherwise, false.
     */
    boolean isValidUser(User user);

    /**
     * Ensures the creation of a default user if they are not already present in the database.
     * <p>
     * This method checks for the existence of a user with a given email. If the user does not exist,
     * it creates and persists a new user with the provided details and assigns them the specified role.
     *
     * @param name The name of the user to be created.
     * @param email The email of the user, used for checking existence and creating the user.
     * @param age The age of the user.
     * @param password The password for the user.
     * @param roleName The name of the role to be assigned to the user.
     */
    void createDefaultUserIfNotFound(String name, String email, int age, String password, String roleName);

    /**
     * Converts a UserDTO object into a User entity.
     * <p>
     * This method is responsible for transforming the data transfer object received from
     * the client into an entity that can be managed by the ORM and persisted in the database.
     * It ensures the data from the UserDTO is correctly mapped to a User entity.
     * </p>
     *
     * @param userDTO The UserDTO object to be converted.
     * @return A User entity populated with the data from the provided UserDTO.
     */
    User convertToUserEntity(UserDTO userDTO);

    /**
     * Converts a User entity into a UserResponseDTO object.
     * <p>
     * This method is used for transforming the User entity into a data transfer object
     * suitable for sending as a response to the client. This includes omitting sensitive
     * information like passwords and focusing on data relevant to the client.
     * </p>
     *
     * @param user The User entity to be converted.
     * @return A UserResponseDTO populated with the data from the provided User entity.
     */
    UserResponseDTO convertToResponseDTO(User user);

    /**
     * Converts a list of User entities into a list of UserResponseDTOs.
     * <p>
     * This method processes a list of User entities, converting each one into a
     * UserResponseDTO. It's useful for batch processing of User entities, especially
     * when responding to requests that retrieve multiple users.
     * </p>
     *
     * @param users The list of User entities to be converted.
     * @return A list of UserResponseDTOs corresponding to the provided User entities.
     */
    List<UserResponseDTO> convertUsersToResponseDTOs(List<User> users);
}
