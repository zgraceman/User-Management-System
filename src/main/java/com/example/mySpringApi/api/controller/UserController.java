package com.example.mySpringApi.api.controller;

import com.example.mySpringApi.model.dto.UserDTO;
import com.example.mySpringApi.model.dto.UserResponseDTO;
import com.example.mySpringApi.response.ResponseHandler;
import com.example.mySpringApi.model.User;
import com.example.mySpringApi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserController is a REST controller that exposes endpoints for interacting with User resources.
 * It routes incoming HTTP requests to appropriate handler methods.
 *
 * This class is annotated with @RestController, indicating it's a RESTful controller.
 * The @RequestMapping("/userAPI") annotation is used at the class level to map the URL prefix for all handler methods
 * in this class.
 *
 * This controller uses DTOs for data coming in and going out of the system to provide
 * a separation of concerns between the API's data structure and the internal data model.
 *
 */
@RestController
@RequestMapping("/userAPI")
@Slf4j
@Tag(name = "User Operations", description = "CRUD operations related to User")
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
     * @return a ResponseEntity containing the UserResponseDTO if found, and an associated message and HTTP status code.
     */
    @Operation(
            summary = "Get a User by their ID",
            description = "Fetches user details by ID from the database. Returns a single user object.")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getUser(@PathVariable int id) {
        User user = userService.getUser(id);
        UserResponseDTO responseDTO = convertToResponseDTO(user);
        return ResponseHandler.generateResponse("User fetched successfully", HttpStatus.OK, responseDTO);
    }

    /**
     * Retrieves a User by email.
     * @param email the email of the User to retrieve
     * @return a ResponseEntity containing the UserResponseDTO if found, and an associated message and HTTP status code.
     */
    @Operation(
            summary = "Get a User by their email",
            description = "Fetches user details by Email from the database. Returns a single user object.")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/email/{email}")
    public ResponseEntity<Object> getUser(@PathVariable String email) {
        log.info("I am in the getUser /email/{email} controller method");
        User user = userService.getUser(email);
        UserResponseDTO responseDTO = convertToResponseDTO(user);
        return ResponseHandler.generateResponse("User fetched successfully", HttpStatus.OK, responseDTO);
    }

    /**
     * Fetches all Users.
     *
     * This method handles GET requests at the "/userAPI" endpoint. It returns a list of all
     * users from the data store or an appropriate error response if no users exist.
     *
     * @return a ResponseEntity containing a list of UserResponseDTOs representing all users or an associated message and HTTP status code.
     */
    @Operation(
            summary = "Fetches all Users",
            description = "Fetches all user details from the database. Returns a list of user objects.")
    @ApiResponse(responseCode = "200", description = "Successfully fetched all users")
    @ApiResponse(responseCode = "204", description = "No users exist")
    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        log.info("I am in the getAllUsers controller method");
        List<User> users = userService.getAllUsers();
        List<UserResponseDTO> responseDTOList = users.stream()  // converts each user object in list to
                .map(this::convertToResponseDTO)                // UserResponseDTO and returns list of
                .collect(Collectors.toList());                  // UserResponseDTO
        return ResponseHandler.generateResponse("All users fetched", HttpStatus.OK, responseDTOList);
    }

    /**
     * Endpoint to create a new user.
     *
     * This method is mapped to the "/createUser" endpoint and facilitates HTTP POST requests.
     * The method consumes a UserDTO object from the request body, which signifies the new user's data.
     * This DTO is subsequently converted to a User entity and handed over to the UserService's
     * createUser method for persistence. After the user is saved in the database,
     * the resultant User entity, inclusive of any modifications made during the database operation
     * (e.g., the auto-generated ID), is transformed into a UserResponseDTO and returned in the response.
     *
     * @param userDTO A UserDTO object contained in the request body, representing the data of the new user.
     * @return a ResponseEntity containing the UserResponseDTO representing the created user, and an associated message and HTTP status code.
     */
    @Operation(
            summary = "Create a new user",
            description = "Creates a new user in the database and returns the created user object.")
    @ApiResponse(responseCode = "201", description = "User successfully created")
    @ApiResponse(responseCode = "400", description = "Bad request - validation error")
    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDTO userDTO) {
        log.warn("I am in the createUser controller method");
        User user = convertToUserEntity(userDTO);
        User createdUser = userService.createUser(user);
        UserResponseDTO responseDTO = convertToResponseDTO(createdUser);
        return ResponseHandler.generateResponse("User successfully created", HttpStatus.CREATED, responseDTO);
    }

    /**
     * Endpoint to update an existing user.
     *
     * Mapped to the "/updateUser" endpoint, this method entertains HTTP PUT requests.
     * The method acquires a UserDTO from the request body that should already encompass the user's ID
     * (corresponding to an extant user). This DTO is then morphed into a User entity and relayed to
     * the UserService's updateUser method for the purpose of updating the existing record in the database.
     * The updated User entity, after the persistence operation, is subsequently converted to a UserResponseDTO
     * and included in the response.
     *
     * If there's an absence of a User record with the stipulated ID in the database, the updateUser
     * method might throw an EntityNotFoundException.
     *
     * @param userDTO The UserDTO embedded in the request body, expected to have the ID of the user slated for an update.
     * @return a ResponseEntity with the UserResponseDTO showcasing the updated user, accompanied by a message and an HTTP status.
     */
    @Operation(
            summary = "Update an existing user by ID",
            description = "Updates an existing user in the database by ID and returns the updated user object.")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "400", description = "Bad request - validation error")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PutMapping("/updateUser")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO) {
        log.warn("I am in the updateUser controller method");
        User user = convertToUserEntity(userDTO);
        User updatedUser = userService.updateUser(user);
        UserResponseDTO responseDTO = convertToResponseDTO(updatedUser);
        return ResponseHandler.generateResponse("User updated successfully", HttpStatus.OK, responseDTO);
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
    @Operation(
            summary = "Delete a user by ID",
            description = "Deletes a user by ID from the database and returns a confirmation message.")
    @ApiResponse(responseCode = "200", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable int id) {
        log.warn("I am in the deleteUser /deleteUser/{id} controller method");
        userService.deleteUser(id);
        return ResponseHandler.generateResponse("User deleted successfully", HttpStatus.OK, null);
    }

    // Helper Methods

    /**
     * Converts a UserDTO object into a User entity.
     *
     * This method is useful for transforming the data transfer object received from
     * the client into an entity that can be managed by the ORM and persisted in the database.
     *
     * Note: The password from the DTO is set directly to the User entity.
     * In a real-world scenario, you would ideally hash/encrypt the password before setting it.
     *
     * @param userDTO The UserDTO object to be converted.
     * @return A User entity populated with the data from the provided UserDTO.
     */
    private User convertToUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.id());
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setAge(userDTO.age());
        user.setPassword(userDTO.rawPassword()); // This would ideally encrypt the password before setting.
        return user;
    }

    /**
     * Converts a User entity into a UserResponseDTO object.
     *
     * This method is useful for transforming the persisted User entity into a
     * data transfer object that can be sent as a response to the client. This ensures that
     * only the necessary data (excluding sensitive information like passwords) is exposed
     * to the client.
     *
     * @param user The User entity to be converted.
     * @return A UserResponseDTO populated with the data from the provided User entity.
     */
    private UserResponseDTO convertToResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge()
        );
    }
}