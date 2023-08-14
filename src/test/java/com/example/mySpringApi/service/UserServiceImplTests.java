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

class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void cleanUp() throws Exception {
        closeable.close();  // This resets the mocks
    }

    // Tests for getUser(Integer id)

    @Test
    @Disabled
    void getUserById_validId_shouldReturnUser() {
        // Implementation ...
    }

    @Test
    @Disabled
    void getUserById_nonExistingId_shouldThrowUserNotFoundException() {
        // Implementation ...
    }

    // Tests for getUser(String name)

    @Test
    @Disabled
    void getUserByName_validName_shouldReturnUser() {
        // Implementation ...
    }

    @Test
    @Disabled
    void getUserByName_nonExistingName_shouldThrowUserNotFoundException() {
        // Implementation ...
    }

    // Tests for createUser(User user)

    @Test
    @Disabled
    void createUser_validUser_shouldReturnCreatedUser() {
        // Implementation ...
    }

    @Test
    @Disabled
    void createUser_invalidUser_shouldThrowInvalidUserInputException() {
        // Implementation ...
    }

    @Test
    @Disabled
    void createUser_existingEmail_shouldThrowUserAlreadyExistsException() {
        // Implementation ...
    }

    // Tests for updateUser(User user)

    @Test
    @Disabled
    void updateUser_validUser_shouldReturnUpdatedUser() {
        // Implementation ...
    }

    @Test
    @Disabled
    void updateUser_invalidUser_shouldThrowInvalidUserInputException() {
        // Implementation ...
    }

    @Test
    @Disabled
    void updateUser_nonExistingId_shouldThrowUserNotFoundException() {
        // Implementation ...
    }

    @Test
    @Disabled
    void updateUser_existingEmailForDifferentUser_shouldThrowUserAlreadyExistsException() {
        // Implementation ...
    }

    // Tests for deleteUser(int id)

    @Test
    @Disabled
    void deleteUser_validId_shouldSuccessfullyDeleteUser() {
        // Implementation ...
    }

    @Test
    @Disabled
    void deleteUser_nonExistingId_shouldThrowUserNotFoundException() {
        // Implementation ...
    }

    // Tests for getAllUsers()

    @Test
    @Disabled
    void getAllUsers_withExistingUsers_shouldReturnListOfUsers() {
        // Implementation ...
    }

    @Test
    @Disabled
    void getAllUsers_noUsersInDatabase_shouldReturnEmptyList() {
        // Implementation ...
    }
}