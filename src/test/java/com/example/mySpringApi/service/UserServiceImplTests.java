package com.example.mySpringApi.service;

import com.example.mySpringApi.exception.InvalidUserInputException;
import com.example.mySpringApi.exception.UserAlreadyExistsException;
import com.example.mySpringApi.exception.UserNotFoundException;
import com.example.mySpringApi.model.User;
import com.example.mySpringApi.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * This class serves as the test suite for methods in UserServiceImpl.
 *
 * It leverages the capabilities of JUnit 5 and Mockito for the testing of the service layer. JUnit 5 offers the core testing annotations
 * and functionalities. In contrast, Mockito is instrumental in creating mock objects and specifying their behavior.
 *
 * Annotations used:
 * - @Mock: Creates a mock instance of the annotated class. In this suite, it mocks the UserRepository.
 * - @InjectMocks: Instantiates the test subject and injects the mocked fields into it. In this context, it's for UserServiceImpl.
 * - @BeforeEach: Indicates that the annotated method should run before each test in this class.
 * - @AfterEach: Indicates that the annotated method should run after each test in this class.
 */
class UserServiceImplTests {

    @Mock  // mocked instance of UserRepository used to simulate interactions with the data
    private UserRepository userRepository;  // layer without actually hitting the database.


    @InjectMocks  // An instance of UserServiceImpl where the mocked UserRepository will be injected.
    private UserServiceImpl userService;

    private AutoCloseable closeable; // resource which helps in resetting the mock states after each test execution.

    private User testUser;

    /**
     * This method runs before each test execution. It sets up mock initialization
     * and prepares common test data to be used in test methods.
     */
    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        testUser = new User("Testo", 999, "testo@example.com");
    }

    /**
     * This method runs after each test execution. It ensures that any resources
     * initialized in the setup are properly closed or cleaned up, ensuring no side
     * effects for subsequent tests.
     */
    @AfterEach
    void cleanUp() throws Exception {
        closeable.close();  // This resets the mocks
    }

    /*
    * -----------------------------
    * TESTS FOR getUser(Integer id)
    * -----------------------------
    */

    /**
     * Test for retrieving a user by a valid ID.
     * It should successfully return the expected user.
     */
    @Test
    void getUserById_validId_shouldReturnUser() {
        // Given
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));  // Mock

        // When
        User retrievedUser = userService.getUser(1);

        // Then
        assertNotNull(retrievedUser);
        assertEquals(testUser, retrievedUser);
    }

    /**
     * Test for attempting to retrieve a user by an ID that does not exist in the system.
     * It should throw a UserNotFoundException.
     */
    @Test
    void getUserById_nonExistingId_shouldThrowUserNotFoundException() {
        // Given
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());  // Mock

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.getUser(1));
    }

    /*
     * ------------------------------
     * TESTS FOR getUser(String name)
     * ------------------------------
     */

    @Test
    @Disabled
    void getUserByName_validName_shouldReturnUser() {
        // Given
        // When
        // Then
    }

    @Test
    @Disabled
    void getUserByName_nonExistingName_shouldThrowUserNotFoundException() {
        // Given
        // When
        // Then
    }

    /*
     * -------------------------------
     * TESTS FOR createUser(User user)
     * -------------------------------
     */

    @Test
    @Disabled
    void createUser_validUser_shouldReturnCreatedUser() {
        // Given
        // When
        // Then
    }

    @Test
    @Disabled
    void createUser_invalidUser_shouldThrowInvalidUserInputException() {
        // Given
        // When
        // Then
    }

    @Test
    @Disabled
    void createUser_existingEmail_shouldThrowUserAlreadyExistsException() {
        // Given
        // When
        // Then
    }

    /*
     * -------------------------------
     * TESTS FOR updateUser(User user)
     * -------------------------------
     */

    @Test
    @Disabled
    void updateUser_validUser_shouldReturnUpdatedUser() {
        // Given
        // When
        // Then
    }

    @Test
    @Disabled
    void updateUser_invalidUser_shouldThrowInvalidUserInputException() {
        // Given
        // When
        // Then
    }

    @Test
    @Disabled
    void updateUser_nonExistingId_shouldThrowUserNotFoundException() {
        // Given
        // When
        // Then
    }

    @Test
    @Disabled
    void updateUser_existingEmailForDifferentUser_shouldThrowUserAlreadyExistsException() {
        // Given
        // When
        // Then
    }

    /*
     * ----------------------------
     * TESTS FOR deleteUser(int id)
     * ----------------------------
     */

    @Test
    @Disabled
    void deleteUser_validId_shouldSuccessfullyDeleteUser() {
        // Given
        // When
        // Then
    }

    @Test
    @Disabled
    void deleteUser_nonExistingId_shouldThrowUserNotFoundException() {
        // Given
        // When
        // Then
    }

    /*
     * -----------------------
     * TESTS FOR getAllUsers()
     * -----------------------
     */

    @Test
    @Disabled
    void getAllUsers_withExistingUsers_shouldReturnListOfUsers() {
        // Given
        // When
        // Then
    }

    @Test
    @Disabled
    void getAllUsers_noUsersInDatabase_shouldReturnEmptyList() {
        // Given
        // When
        // Then
    }
}