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
        testUser.setPassword("SomeTestPassword");
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
        mockNonExistentUserInRepository(1);  // Mock

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.getUser(1));
    }



    /*
     * ------------------------------
     * TESTS FOR getUser(String name)
     * ------------------------------
     */

    /**
     * Test for retrieving a user by a valid email.
     * Expectation: The user should be successfully retrieved if the email exists.
     */
    @Test
    void getUserByEmail_validEmail_shouldReturnUser() {
        // Given
        mockUserInRepository(testUser, 1);

        // When
        User retrievedUser = userService.getUser("testo@example.com");

        // Then
        assertNotNull(retrievedUser);
        assertEquals(testUser, retrievedUser);
    }

    /**
     * Test for retrieving a user by a email that doesn't exist in the repository.
     * Expectation: A UserNotFoundException should be thrown.
     */
    @Test
    void getUserByEmail_nonExistingEmail_shouldThrowUserNotFoundException() {
        // Given
        mockNonExistentUserInRepository(1);  // Mock

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.getUser("UnknownEmail@example.com"));
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
        mockSaveUser(testUser, testUser);  // Mock

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
        updatedUser.setPassword("SomeUpdatedPassword");

        mockUserInRepository(testUser, updatedUser.getId());
        mockSaveUser(updatedUser, updatedUser);

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

        mockNonExistentUserInRepository(nonExistentUser.getId());

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

        // Mock the interactions with the UserRepository
        mockUserInRepository(userToUpdate, userToUpdate.getId());
        mockUserInRepository(existingUserWithSameEmail, existingUserWithSameEmail.getId());

        // When & Then
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
        mockNonExistentUserInRepository(nonExistentUserId);

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

    /*
     * -------------------------------
     * TESTS FOR isValidUser(User user)
     * -------------------------------
     */

    /**
     * Test to verify that a user with valid information is correctly determined to be valid.
     * Expectation: The method should return true.
     */
    @Test
    void isValidUser_validUser_shouldReturnTrue() {
        // Given
        User validUser = new User("John Doe", 1234, "johndoe@example.com");

        // When
        boolean isValid = userService.isValidUser(validUser);

        // Then
        assertTrue(isValid);
    }

    /**
     * Test for determining the validity of a user with a null name.
     * Expectation: The method should return false.
     */
    @Test
    void isValidUser_nullName_shouldReturnFalse() {
        // Given
        User userWithNullName = new User(null, 1235, "invalid@example.com");

        // When
        boolean isValid = userService.isValidUser(userWithNullName);

        // Then
        assertFalse(isValid);
    }

    /**
     * Test for determining the validity of a user with an empty name.
     * Expectation: The method should return false.
     */
    @Test
    void isValidUser_emptyName_shouldReturnFalse() {
        // Given
        User userWithEmptyName = new User("", 1236, "emptyname@example.com");

        // When
        boolean isValid = userService.isValidUser(userWithEmptyName);

        // Then
        assertFalse(isValid);
    }

    /**
     * Test for determining the validity of a user with an invalid email format.
     * Expectation: The method should return false.
     */
    @Test
    void isValidUser_invalidEmailFormat_shouldReturnFalse() {
        // Given
        User userWithInvalidEmail = new User("Invalid Email User", 1237, "invalidemail");

        // When
        boolean isValid = userService.isValidUser(userWithInvalidEmail);

        // Then
        assertFalse(isValid);
    }

    /**
     * Test if the User's name is too short.
     *
     * Given a User object with a name of 1 characters,
     * when checking its validity using the isValidUser method,
     * then the method should return false.
     */
    @Test
    void whenNameIsTooShort_thenIsValidUserShouldReturnFalse() {
        // Given
        User userWithShortName = new User("J", 101, "j@example.com");

        // When
        boolean isValid = userService.isValidUser(userWithShortName);

        // Then
        assertFalse(isValid);
    }

    /**
     * Test if the User's name is too long.
     *
     * Given a User object with a name exceeding 50 characters,
     * when checking its validity using the isValidUser method,
     * then the method should return false.
     */
    @Test
    void whenNameIsTooLong_thenIsValidUserShouldReturnFalse() {
        // Given
        User userWithLongName = new User("J".repeat(51), 101, "longname@example.com");

        // When
        boolean isValid = userService.isValidUser(userWithLongName);

        // Then
        assertFalse(isValid);
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

    /**
     * Mocks the save method of the UserRepository to simulate saving a user.
     *
     * @param inputUser The user that is expected to be passed to the save method.
     * @param returnedUser The user that should be returned when save is called.
     */
    private void mockSaveUser(User inputUser, User returnedUser) {
        when(userRepository.save(inputUser)).thenReturn(returnedUser);
    }

    /**
     * Helper method to mock the behavior of the UserRepository for a non-existing user.
     *
     * @param userId The user ID that does not exist in the repository.
     */
    private void mockNonExistentUserInRepository(int userId) {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        when(userRepository.existsById(userId)).thenReturn(false);
    }
}