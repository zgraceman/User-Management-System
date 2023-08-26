package com.example.mySpringApi.api.controller;

import com.example.mySpringApi.model.User;
import com.example.mySpringApi.service.UserService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        given(userService.getUser(nonExistingUserId)).willReturn(null);

        // When & Then
        mockMvc.perform(get("/userAPI/id/" + nonExistingUserId))
                .andExpect(status().isNotFound());
    }

    /*
     * ------------------------------
     * TESTS FOR getUser(String name)
     * ------------------------------
     */

    @Test
    public void getUserByName_existingName_shouldReturnUser() {
        // ...
    }

    @Test
    public void getUserByName_nonExistingName_shouldReturnNotFound() {
        // ...
    }

    /*
     * -----------------------
     * TESTS FOR getAllUsers()
     * -----------------------
     */

    @Test
    public void getAllUsers_usersInDatabase_shouldReturnUserList() {
        // ...
    }

    @Test
    public void getAllUsers_noUsersInDatabase_shouldReturnEmptyList() {
        // ...
    }

    /*
     * -------------------------------
     * TESTS FOR createUser(User user)
     * -------------------------------
     */

    @Test
    public void createUser_validUser_shouldReturnCreatedUser() {
        // ...
    }

    @Test
    public void createUser_invalidUser_shouldReturnValidationError() { // if you have validations
        // ...
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