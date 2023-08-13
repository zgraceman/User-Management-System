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

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager; // For setting up database state

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User("TestUser", 24, "TestUser@gmail.com");

        entityManager.persist(testUser);
        entityManager.flush();
    }

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll(); // This will clear the h2 database after each test
    }

    @Test
    public void whenValidName_thenUserShouldBeFound() {
        Optional<User> found = userRepository.findByName(testUser.getName());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(testUser.getName());
    }

    @Test
    public void whenValidEmail_thenUserShouldBeFound() {
        Optional<User> found = userRepository.findByEmail(testUser.getEmail());

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    public void whenSavingUser_thenUserShouldBePersisted() {
        User newUser = new User("AliceTest", 60, "aliceTest@example.com");

        User savedUser = userRepository.save(newUser);
        Optional<User> found = userRepository.findById(savedUser.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(newUser.getName());
    }
}