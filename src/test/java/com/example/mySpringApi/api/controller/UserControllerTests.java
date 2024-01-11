package com.example.mySpringApi.api.controller;

import com.example.mySpringApi.exception.UserNotFoundException;
import com.example.mySpringApi.model.Role;
import com.example.mySpringApi.model.User;
import com.example.mySpringApi.model.dto.UserDTO;
import com.example.mySpringApi.model.dto.UserResponseDTO;
import com.example.mySpringApi.service.RoleServiceImpl;
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

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * UserControllerTests is responsible for testing the behavior of the UserController.
 * <p>
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

    @MockBean
    private RoleServiceImpl roleServiceImpl;

    private User mockUser;
    private Set<Role> userRole;


    @BeforeEach
    void setUp() {
        mockUser = new User("John", 40, "john@example.com");
        mockUser.setId(1);
        mockUser.setPassword("Password123!");

        // Initialize roles for the mockUser
        userRole = roleServiceImpl.findRolesByNames(Collections.singleton("USER")); // Example method
        mockUser.setRoles(userRole);
    }

    private UserResponseDTO createMockUserResponseDTO(User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                roleNames
        );
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
        // Assuming you have a method to create a UserResponseDTO from a User
        UserResponseDTO mockUserResponseDTO = createMockUserResponseDTO(mockUser);

        // Mock the behavior of UserService conversion method
        given(userService.convertToResponseDTO(mockUser)).willReturn(mockUserResponseDTO);

        // Given the mock behavior of UserService.
        given(userService.getUser(1)).willReturn(mockUser);

        // When & Then
        mockMvc.perform(get("/userAPI/id/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.id").value(mockUserResponseDTO.id()))
                .andExpect(jsonPath("$.data.name").value(mockUserResponseDTO.name()))
                .andExpect(jsonPath("$.data.email").value(mockUserResponseDTO.email()))
                .andExpect(jsonPath("$.data.age").value(mockUserResponseDTO.age()))
                .andExpect(jsonPath("$.data.password").doesNotExist());
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
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").doesNotExist()) // or use .isEmpty() depending on your implementation
                .andExpect(jsonPath("$.message").value("User not found"))
                .andExpect(jsonPath("$.status").value(404));
    }

    /*
     * ------------------------------
     * TESTS FOR getUser(String name)
     * ------------------------------
     */

    /**
     * Test to verify behavior when an existing user email is provided.
     *
     * @throws Exception if any MVC or JSON parsing exception occurs.
     */
    @Test
    public void getUserByEmail_existingEmail_shouldReturnUser() throws Exception {
        // Create a mock UserResponseDTO from the mockUser
        UserResponseDTO mockUserResponseDTO = createMockUserResponseDTO(mockUser);

        // Mock the behavior of UserService's getUser method
        given(userService.getUser("John@example.com")).willReturn(mockUser);

        // Mock the behavior of UserService's convertToResponseDTO method
        given(userService.convertToResponseDTO(mockUser)).willReturn(mockUserResponseDTO);

        // When & Then
        mockMvc.perform(get("/userAPI/email/John@example.com"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.id").value(mockUserResponseDTO.id()))
                .andExpect(jsonPath("$.data.name").value(mockUserResponseDTO.name()))
                .andExpect(jsonPath("$.data.email").value(mockUserResponseDTO.email()))
                .andExpect(jsonPath("$.data.age").value(mockUserResponseDTO.age()))
                .andExpect(jsonPath("$.data.password").doesNotExist());;
    }

    /**
     * Test to verify behavior when a non-existing user email is provided.
     *
     * @throws Exception if any MVC or JSON parsing exception occurs.
     */
    @Test
    public void getUserByEmail_nonExistingEmail_shouldReturnNotFound() throws Exception {
        // Given
        String nonExistingUserEmail = "UnknownEmail@example.com";
        given(userService.getUser(nonExistingUserEmail)).willThrow(new UserNotFoundException());

        // When & Then
        mockMvc.perform(get("/userAPI/email/" + nonExistingUserEmail))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value("User not found"))
                .andExpect(jsonPath("$.status").value(404));
    }

    /*
     * -----------------------
     * TESTS FOR getAllUsers()
     * -----------------------
     */

    /**
     * Test to verify behavior when there are users in the database.
     * <p>
     * This test ensures that the correct HTTP status and user list are returned
     * when there are users present in the database.
     *
     * @throws Exception if any MVC or JSON parsing exception occurs.
     */
    @Test
    public void getAllUsers_usersInDatabase_shouldReturnUserList() throws Exception {
        // Create user list
        User user1 = new User("John", 40, "john@example.com");
        user1.setRoles(userRole); // Add roles to user1

        User user2 = new User("Jane", 35, "jane@example.com");
        user2.setRoles(userRole); // Add roles to user2

        List<User> users = Arrays.asList(user1, user2);

        // Create mock UserResponseDTOs
        UserResponseDTO userResponseDTO1 = createMockUserResponseDTO(user1);
        UserResponseDTO userResponseDTO2 = createMockUserResponseDTO(user2);

        // Mocking the behavior of userService
        given(userService.getAllUsers()).willReturn(users);

        // Mocking the conversion to UserResponseDTOs
        given(userService.convertUsersToResponseDTOs(users)).willReturn(Arrays.asList(userResponseDTO1, userResponseDTO2));

        // Perform test actions and assertions
        mockMvc.perform(get("/userAPI"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("All users fetched"))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].name").value("John"))
                .andExpect(jsonPath("$.data[0].email").value("john@example.com"))
                .andExpect(jsonPath("$.data[1].name").value("Jane"))
                .andExpect(jsonPath("$.data[1].email").value("jane@example.com"))
                .andExpect(jsonPath("$.data[0].password").doesNotExist())
                .andExpect(jsonPath("$.data[1].password").doesNotExist());
    }

    /**
     * Test to verify behavior when the database has no users.
     * <p>
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

    /**
     * Test to verify the creation of a user with valid details.
     * <p>
     * This test ensures that when a valid user is created through the createUser endpoint, the correct HTTP status is returned along with the details of the created user in the response.
     *
     * @throws Exception if any MVC or JSON parsing exception occurs.
     */
    @Test
    public void createUser_validUser_shouldReturnCreatedUser() throws Exception {
        // Given a valid UserDTO
        Set<String> roles = Set.of("ADMIN");
        UserDTO newUserDTO = new UserDTO(0, "Alice",  30,"alice@example.com", "securePassword123!", roles);

        // Given a User entity that the service layer would return
        User newUser = new User("Alice", 30, "alice@example.com");
        newUser.setId(1);  // Assuming the user gets an ID after being saved
        newUser.setRoles(userRole);

        // Mock the conversion of UserDTO to User entity
        given(userService.convertToUserEntity(newUserDTO)).willReturn(newUser);

        // Mocking the userService to return the created user when called
        given(userService.createUser(newUser)).willReturn(newUser);

        // Create a mock UserResponseDTO
        UserResponseDTO mockUserResponseDTO = createMockUserResponseDTO(newUser);

        // Mock the conversion of User to UserResponseDTO
        given(userService.convertToResponseDTO(newUser)).willReturn(mockUserResponseDTO);

        // Converting the User object to JSON format
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(newUserDTO);

        // When & Then
        mockMvc.perform(post("/userAPI/createUser")
                        .contentType("application/json")
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User successfully created"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Alice"))
                .andExpect(jsonPath("$.data.email").value("alice@example.com"))
                .andExpect(jsonPath("$.data.age").value(30))
                .andExpect(jsonPath("$.data.password").doesNotExist());
    }

    /**
     * Test to verify the validation error during the creation of a user with invalid details.
     * <p>
     * This test ensures that when an invalid user is created through the createUser endpoint, the correct HTTP status and validation error messages are returned to indicate the validation failure.
     *
     * @throws Exception if any MVC or JSON parsing exception occurs.
     */
    @Test
    public void createUser_invalidUser_shouldReturnValidationError() throws Exception { // if you have validations
        // Prepare a user object with invalid data
        Set<String> roles = Set.of("ADMIN");
        UserDTO invalidUserDTO = new UserDTO(0, "x", 10000, "notAnEmail", "pwD!1", roles);

        // Convert the invalidUser object to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String invalidUserJson = objectMapper.writeValueAsString(invalidUserDTO);

        // Perform a POST request with the invalidUserJson and expect a bad request status (HTTP 400)
        mockMvc.perform(post("/userAPI/createUser")
                        .contentType("application/json")
                        .content(invalidUserJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed for the request."))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.data.name").value("Name must be between 2 and 50 characters."))
                .andExpect(jsonPath("$.data.age").value("Age value is unrealistic."))
                .andExpect(jsonPath("$.data.email").value("Invalid email format."))
                .andExpect(jsonPath("$.data.rawPassword").value("Password must be at least 8 characters long."));
    }

    /*
     * -------------------------------
     * TESTS FOR updateUser(User user)
     * -------------------------------
     */

    /**
     * Test to verify updating an existing user.
     * <p>
     * This test ensures that when an existing user's details are updated via the updateUser endpoint, the correct HTTP status is returned along with the updated user information in the response.
     *
     * @throws Exception if any MVC or JSON parsing exception occurs.
     */
    @Test
    public void updateUser_existingUser_shouldReturnUpdatedUser() throws Exception {
        // Given an existing user and updated details
        Set<String> roles = Set.of("ADMIN");
        UserDTO updatedUserDTO = new UserDTO(1, "John Updated", 45, "john_updated@example.com", "NewPassword@123", roles);

        User updatedUser = new User("John Updated", 45, "john_updated@example.com");
        updatedUser.setId(1);
        updatedUser.setRoles(userRole);

        given(userService.updateUser(any(User.class))).willReturn(updatedUser);

        // Convert the updatedUser object to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String updatedUserJson = objectMapper.writeValueAsString(updatedUserDTO);

        // When & Then
        mockMvc.perform(put("/userAPI/updateUser")
                        .contentType("application/json")
                        .content(updatedUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User updated successfully"))  // Adjusted success message
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("John Updated"))
                .andExpect(jsonPath("$.data.email").value("john_updated@example.com"))
                .andExpect(jsonPath("$.data.age").value(45))
                .andExpect(jsonPath("$.data.password").doesNotExist());
    }

    /**
     * Test to verify the case of updating a non-existing user.
     * <p>
     * This test ensures that an attempt to update a non-existing user through the updateUser endpoint returns the correct HTTP status and an appropriate error message.
     *
     * @throws Exception if any MVC or JSON parsing exception occurs.
     */
    @Test
    public void updateUser_nonExistingUser_shouldReturnNotFound() throws Exception {
        // Given a non-existing user's details
        Set<String> roles = Set.of("ADMIN");
        UserDTO nonExistingUserDTO = new UserDTO(999999, "Non Existent", 50,"non_existent@example.com", "securePassword123!", roles);

        // Since we're testing the update operation, we're assuming the UserNotFoundException gets thrown at the service layer.
        // So we need to configure our mock to throw this exception when the updateUser method is called.
        given(userService.updateUser(any(User.class))).willThrow(new UserNotFoundException("User not found"));

        // Convert the nonExistingUserDTO object to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String nonExistingUserJson = objectMapper.writeValueAsString(nonExistingUserDTO);

        // When & Then
        mockMvc.perform(put("/userAPI/updateUser")
                        .contentType("application/json")
                        .content(nonExistingUserJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }


    /*
     * ----------------------------
     * TESTS FOR deleteUser(int id)
     * ----------------------------
     */

    /**
     * Test to verify deleting a user with an existing ID.
     * <p>
     * This test ensures that deleting a user with an existing ID through the deleteUser endpoint returns the correct HTTP status along with a success message indicating the user was deleted.
     *
     * @throws Exception if any MVC or JSON parsing exception occurs.
     */
    @Test
    public void deleteUser_existingId_shouldReturnDeletedSuccessfully() throws Exception {
        // Given an existing user ID
        int existingUserId = 1;

        // Mocking the userService to not throw any exception when called with existingUserId
        willDoNothing().given(userService).deleteUser(existingUserId);

        // When & Then
        mockMvc.perform(delete("/userAPI/deleteUser/" + existingUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User deleted successfully"))
                .andExpect(jsonPath("$.data").doesNotExist());


        verify(userService, times(1)).deleteUser(existingUserId);
    }

    /**
     * Test to verify the case of deleting a non-existing user.
     * <p>
     * This test ensures that an attempt to delete a non-existing user ID through the deleteUser endpoint returns the correct HTTP status and an appropriate error message.
     *
     * @throws Exception if any MVC or JSON parsing exception occurs.
     */
    @Test
    public void deleteUser_nonExistingId_shouldReturnNotFound() throws Exception {
        // Given a non-existing user ID
        int nonExistingUserId = 999999; // some ID that doesn't exist

        // Mocking the userService to throw UserNotFoundException when called with nonExistingUserId
        willThrow(new UserNotFoundException()).given(userService).deleteUser(nonExistingUserId);

        // When & Then
        mockMvc.perform(delete("/userAPI/deleteUser/" + nonExistingUserId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"))
                .andExpect(jsonPath("$.data").doesNotExist());

        verify(userService, times(1)).deleteUser(nonExistingUserId);
    }
}