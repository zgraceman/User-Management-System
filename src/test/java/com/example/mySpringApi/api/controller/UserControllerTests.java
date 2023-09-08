package com.example.mySpringApi.api.controller;

import com.example.mySpringApi.exception.UserNotFoundException;
import com.example.mySpringApi.model.User;
import com.example.mySpringApi.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * UserControllerTests is responsible for testing the behavior of the UserController.
 *
 * This class uses Spring's WebMvcTest to load only the web layer and not the complete
 * context. It leverages MockMvc to send HTTP requests and assert responses.
 *
 * @WebMvcTest is specialized for testing Spring MVC controllers by
 * disabling full autoconfiguration and applying only configuration relevant to MVC tests.
 */
@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User mockUser;


    @BeforeEach
    void setUp() {
        mockUser = new User("John", 40, "john@example.com");
        mockUser.setId(1);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(userService);
    }

    /*
     * -----------------------------
     * TESTS FOR getUser(Integer id)
     * -----------------------------
     */

    /**
     * Test to verify behavior when an existing user ID is provided.
     *
     * @throws Exception if any MVC or JSON parsing exception occurs.
     */
    @Test
    public void getUserById_existingId_shouldReturnUser() throws Exception {
        // Given the mock behavior of UserService.
        given(userService.getUser(1)).willReturn(mockUser);

        // When & Then
        mockMvc.perform(get("/userAPI/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("John"))
                .andExpect(jsonPath("$.data.email").value("john@example.com"));
    }

    /**
     * Test to verify behavior when a non-existing user ID is provided.
     *
     * @throws Exception if any MVC or JSON parsing exception occurs.
     */
    @Test
    public void getUserById_nonExistingId_shouldReturnNotFound() throws Exception {
        // Given
        int nonExistingUserId = 999999; // some ID that doesn't exist
        given(userService.getUser(nonExistingUserId)).willThrow(new UserNotFoundException());

        // When & Then
        mockMvc.perform(get("/userAPI/id/" + nonExistingUserId))
                .andExpect(status().isNotFound());
    }

    /*
     * ------------------------------
     * TESTS FOR getUser(String name)
     * ------------------------------
     */

    /**
     * Test to verify behavior when an existing user name is provided.
     *
     * @throws Exception if any MVC or JSON parsing exception occurs.
     */
    @Test
    public void getUserByName_existingName_shouldReturnUser() throws Exception {
        // Given the mock behavior of UserService.
        given(userService.getUser("John")).willReturn(mockUser);

        // When & Then
        mockMvc.perform(get("/userAPI/name/John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("John"))
                .andExpect(jsonPath("$.data.email").value("john@example.com"));
    }

    /**
     * Test to verify behavior when a non-existing user name is provided.
     *
     * @throws Exception if any MVC or JSON parsing exception occurs.
     */
    @Test
    public void getUserByName_nonExistingName_shouldReturnNotFound() throws Exception {
        // Given
        String nonExistingUserName = "UnknownName";
        given(userService.getUser(nonExistingUserName)).willThrow(new UserNotFoundException());

        // When & Then
        mockMvc.perform(get("/userAPI/name/" + nonExistingUserName))
                .andExpect(status().isNotFound());
    }

    /*
     * -----------------------
     * TESTS FOR getAllUsers()
     * -----------------------
     */

    /**
     * Test to verify behavior when there are users in the database.
     *
     * This test ensures that the correct HTTP status and user list are returned
     * when there are users present in the database.
     *
     * @throws Exception if any MVC or JSON parsing exception occurs.
     */
    @Test
    public void getAllUsers_usersInDatabase_shouldReturnUserList() throws Exception {
        // Given a list of users.
        List<User> users = Arrays.asList(
                new User("John", 40, "john@example.com"),
                new User("Jane", 35, "jane@example.com")
        );
        given(userService.getAllUsers()).willReturn(users);

        // When & Then
        mockMvc.perform(get("/userAPI"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("All users fetched"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].name").value("John"))
                .andExpect(jsonPath("$.data[0].email").value("john@example.com"))
                .andExpect(jsonPath("$.data[1].name").value("Jane"))
                .andExpect(jsonPath("$.data[1].email").value("jane@example.com"));
    }

    /**
     * Test to verify behavior when the database has no users.
     *
     * This test ensures that the correct HTTP status and an empty list or
     * appropriate message are returned when there are no users in the database.
     *
     * @throws Exception if any MVC or JSON parsing exception occurs.
     */
    @Test
    public void getAllUsers_noUsersInDatabase_shouldReturnEmptyList() throws Exception {
        // Given an empty list.
        given(userService.getAllUsers()).willReturn(new ArrayList<>());

        // When & Then
        mockMvc.perform(get("/userAPI"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("All users fetched"))
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    /*
     * -------------------------------
     * TESTS FOR createUser(User user)
     * -------------------------------
     */

    @Test
    public void createUser_validUser_shouldReturnCreatedUser() throws Exception {
        // Given a valid user
        //User newUser = new User("Alice", 30, "alice@example.com");

        // Mocking the userService to return the created user when called
        given(userService.createUser(any(User.class))).willReturn(mockUser);

        // Converting the User object to JSON format
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(mockUser);

        // When & Then
        mockMvc.perform(post("/userAPI/createUser")
                        .contentType("application/json")
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("John"))
                .andExpect(jsonPath("$.data.email").value("john@example.com"));
    }

    @Test
    public void createUser_invalidUser_shouldReturnValidationError() throws Exception { // if you have validations
        // Prepare a user object with invalid data
        mockUser.setName("x");
        mockUser.setAge(10000);
        mockUser.setEmail("notAnEmail");

        // Convert the invalidUser object to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String invalidUserJson = objectMapper.writeValueAsString(mockUser);

        // Perform a POST request with the invalidUserJson and expect a bad request status (HTTP 400)
        mockMvc.perform(post("/userAPI/createUser")
                        .contentType("application/json")
                        .content(invalidUserJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.name").value("Name must be between 2 and 50 characters."))
                .andExpect(jsonPath("$.data.age").value("Age value is unrealistic."))
                .andExpect(jsonPath("$.data.email").value("Invalid email format."))
                .andExpect(jsonPath("$.message").value("Validation failed for the request."))
                .andExpect(jsonPath("$.status").value(400));
    }

    /*
     * -------------------------------
     * TESTS FOR updateUser(User user)
     * -------------------------------
     */

    @Test
    public void updateUser_existingUser_shouldReturnUpdatedUser() {
        // ...
    }

    @Test
    public void updateUser_nonExistingUser_shouldReturnNotFound() {
        // ...
    }


    /*
     * ----------------------------
     * TESTS FOR deleteUser(int id)
     * ----------------------------
     */

    @Test
    public void deleteUser_existingId_shouldReturnDeletedSuccessfully() {
        // ...
    }

    @Test
    public void deleteUser_nonExistingId_shouldReturnNotFound() {
        // ...
    }
}