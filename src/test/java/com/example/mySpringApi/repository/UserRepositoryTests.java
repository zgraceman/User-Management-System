package com.example.mySpringApi.repository;

import com.example.mySpringApi.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class tests the {@link UserRepository} using an in-memory H2 database.
 *
 * @DataJpaTest is used to set up an embedded database and configure Spring Data JPA.
 */
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager; // For setting up database state

    private User testUser;

    /**
     * Set up common test data before each test.
     * This includes persisting a test User object to the database.
     */
    @BeforeEach
    public void setUp() {
        testUser = new User("TestUser", 24, "TestUser@gmail.com");

        entityManager.persist(testUser);
        entityManager.flush();
    }

    /**
     * Cleans up database after each test.
     * This ensures that data from one test does not interfere with other tests.
     */
    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll(); // This will clear the h2 database after each test
    }

    /**
     * Test that a User can be retrieved by name.
     * This ensures that the findByName method of the UserRepository is functioning as expected.
     */
    @Test
    public void whenValidName_thenUserShouldBeFound() {
        Optional<User> found = userRepository.findByName(testUser.getName());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(testUser.getName());
    }

    /**
     * Test that a User can be retrieved by email.
     * This ensures that the findByEmail method of the UserRepository is functioning as expected.
     */
    @Test
    public void whenValidEmail_thenUserShouldBeFound() {
        Optional<User> found = userRepository.findByEmail(testUser.getEmail());

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo(testUser.getEmail());
    }

    /**
     * Test that when a User is saved, it gets correctly persisted in the database.
     * This ensures that the save method of the UserRepository is functioning as expected.
     */
    @Test
    public void whenSavingUser_thenUserShouldBePersisted() {
        User newUser = new User("AliceTest", 60, "aliceTest@example.com");

        User savedUser = userRepository.save(newUser);
        Optional<User> found = userRepository.findById(savedUser.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(newUser.getName());
    }
}