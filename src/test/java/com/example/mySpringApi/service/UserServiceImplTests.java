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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
        mockUserInRepository(testUser, 1);

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

    /**
     * Test for retrieving a user by a valid name.
     * Expectation: The user should be successfully retrieved if the name exists.
     */
    @Test
    void getUserByName_validName_shouldReturnUser() {
        // Given
        mockUserInRepository(testUser, 1);

        // When
        User retrievedUser = userService.getUser("Testo");

        // Then
        assertNotNull(retrievedUser);
        assertEquals(testUser, retrievedUser);
    }

    /**
     * Test for retrieving a user by a name that doesn't exist in the repository.
     * Expectation: A UserNotFoundException should be thrown.
     */
    @Test
    void getUserByName_nonExistingName_shouldThrowUserNotFoundException() {
        // Given
        when(userRepository.findByName(any())).thenReturn(Optional.empty());  // Mock

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.getUser("UnknownName"));
    }



    /*
     * -------------------------------
     * TESTS FOR createUser(User user)
     * -------------------------------
     */

    /**
     * Test for creating a user with valid input.
     * Expectation: The user should be successfully created and returned.
     */
    @Test
    void createUser_validUser_shouldReturnCreatedUser() {
        // Given
        when(userRepository.save(testUser)).thenReturn(testUser);  // Mock

        // When
        User createdUser = userService.createUser(testUser);

        // Then
        assertNotNull(createdUser);
        assertEquals(testUser, createdUser);
    }

    /**
     * Test for attempting to create a user with invalid input (null name in this case).
     * Expectation: An InvalidUserInputException should be thrown.
     */
    @Test
    void createUser_invalidUser_shouldThrowInvalidUserInputException() {
        // Given
        User invalidUser = new User(null, 1000, "invalid@example.com");

        // When & Then
        assertThrows(InvalidUserInputException.class, () -> userService.createUser(invalidUser));
    }

    /**
     * Test for attempting to create a user with an email that already exists.
     * Expectation: A UserAlreadyExistsException should be thrown.
     */
    @Test
    void createUser_existingEmail_shouldThrowUserAlreadyExistsException() {
        // Given
        mockUserInRepository(testUser, testUser.getId());  // Mock

        // When & Then
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(testUser));
    }

    /*
     * -------------------------------
     * TESTS FOR updateUser(User user)
     * -------------------------------
     */

    /**
     * Test to verify that a user with valid information is successfully updated.
     * Expectation: The returned user should be updated with the new information.
     */
    @Test
    void updateUser_validUser_shouldReturnUpdatedUser() {
        // Given
        User updatedUser = new User("UpdatedTesto", 1001, "updatedtesto@example.com");

        mockUserInRepository(testUser, updatedUser.getId());
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        // When
        User result = userService.updateUser(updatedUser);

        // Then
        assertNotNull(result);
        assertEquals(updatedUser.getName(), result.getName());
        assertEquals(updatedUser.getEmail(), result.getEmail());
        verify(userRepository).save(updatedUser);
    }

    /**
     * Test for attempting to update a user with invalid input (null name in this case).
     * Expectation: An InvalidUserInputException should be thrown.
     */
    @Test
    void updateUser_invalidUser_shouldThrowInvalidUserInputException() {
        // Given
        User invalidUser = new User(null, 1002, "invalid@example.com");

        mockUserInRepository(invalidUser, invalidUser.getId());

        // When & Then
        assertThrows(InvalidUserInputException.class, () -> userService.updateUser(invalidUser));
    }

    /**
     * Test for attempting to update a user that does not exist in the system by its ID.
     * Expectation: A UserNotFoundException should be thrown.
     */
    @Test
    void updateUser_nonExistingId_shouldThrowUserNotFoundException() {
        // Given
        User nonExistentUser = new User("NonExistent", 9999, "nonexistent@example.com");

        when(userRepository.existsById(nonExistentUser.getId())).thenReturn(false);

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(nonExistentUser));
    }

    /**
     * Test for attempting to update a user's email to one that already exists for a different user in the system.
     * Expectation: A UserAlreadyExistsException should be thrown.
     */
    @Test
    void updateUser_existingEmailForDifferentUser_shouldThrowUserAlreadyExistsException() {
        // Given
        User existingUserWithSameEmail = new User("existingUser", 10, "sameEmail@example.com");
        existingUserWithSameEmail.setId(1);  // Make sure to set different IDs

        User userToUpdate = new User("userToUpdate", 10, "sameEmail@example.com");
        userToUpdate.setId(2);  // Different ID than the existing user

        // 2. Mock the interactions with the UserRepository
        when(userRepository.existsById(userToUpdate.getId())).thenReturn(true);
        mockUserInRepository(existingUserWithSameEmail, existingUserWithSameEmail.getId());

        // 3. When & Then
        assertThrows(UserAlreadyExistsException.class, () -> userService.updateUser(userToUpdate));
    }

    /*
     * ----------------------------
     * TESTS FOR deleteUser(int id)
     * ----------------------------
     */

    /**
     * Test for deleting a user with a valid ID.
     * Expectation: The user should be successfully deleted from the repository.
     */
    @Test
    void deleteUser_validId_shouldSuccessfullyDeleteUser() {
        // Given
        int userId = 1;
        mockUserInRepository(testUser, userId);
        doNothing().when(userRepository).deleteById(userId);

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository).deleteById(userId);
    }

    /**
     * Test for attempting to delete a user by an ID that does not exist in the system.
     * Expectation: A UserNotFoundException should be thrown.
     */
    @Test
    void deleteUser_nonExistingId_shouldThrowUserNotFoundException() {
        // Given
        int nonExistentUserId = 2;
        when(userRepository.existsById(nonExistentUserId)).thenReturn(false);

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(nonExistentUserId));
    }

    /*
     * -----------------------
     * TESTS FOR getAllUsers()
     * -----------------------
     */

    /**
     * Test for retrieving all users when there are users present in the database.
     * Expectation: The returned list should contain all users present in the mock repository,
     * and the size of the list should correspond to the number of mock users.
     */
    @Test
    void getAllUsers_withExistingUsers_shouldReturnListOfUsers() {
        // Given
        List<User> users = Arrays.asList(
                new User("Alice", 1, "alice@example.com"),
                new User("Bob", 2, "bob@example.com"),
                new User("Charlie", 3, "charlie@example.com")
        );
        when(userRepository.findAll()).thenReturn(users);

        // When
        List<User> retrievedUsers = userService.getAllUsers();

        // Then
        assertNotNull(retrievedUsers);
        assertEquals(3, retrievedUsers.size());
        assertTrue(retrievedUsers.containsAll(users));
    }

    /**
     * Test for retrieving all users when the database is empty.
     * Expectation: The method should return an empty list, and the list should not be null.
     */
    @Test
    void getAllUsers_noUsersInDatabase_shouldReturnEmptyList() {
        // Given
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<User> retrievedUsers = userService.getAllUsers();

        // Then
        assertNotNull(retrievedUsers);
        assertTrue(retrievedUsers.isEmpty());
    }


    // Helper Methods

    /**
     * Helper method to mock the behavior of the UserRepository for a given user.
     *
     * This method simulates the presence of a specific user in the repository by making the repository return
     * this user for various queries (by ID, by name, by email) and indicating that this user exists.
     *
     * Primarily used within the test class to streamline the setup of the mock UserRepository and reduce redundancy
     * in the test preparations.
     *
     * @param user The user instance that the mocked repository should return for queries.
     * @param userId The user ID associated with the given user, used to mock the findByID() method.
     */
    private void mockUserInRepository(User user, int userId) {
        // Mock the behavior for the findById() method. When the method is called with the provided userId,
        // it will return the given user wrapped in an Optional.
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Mock the behavior for the findByName() method.
        when(userRepository.findByName(user.getName())).thenReturn(Optional.of(user));

        // Mock the behavior for the findByEmail() method.
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Mock the behavior for the existsById() method. When the method is called with the provided userId,
        // it will return true, indicating that a user with the given ID exists in the mock repository.
        when(userRepository.existsById(userId)).thenReturn(true);
    }
}