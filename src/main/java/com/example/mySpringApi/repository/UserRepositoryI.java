package com.example.mySpringApi.repository;

import com.example.mySpringApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserRepositoryI interface provides the functionality for interacting with
 * User entities stored in the database. It extends JpaRepository, leveraging Spring Data JPA
 * to generate the implementation code for common operations automatically.
 *
 * Methods:
 * - findByName(String name): Searches the database for a User entity with the given name.
 *   Returns an Optional object that may contain a User if one with the provided name exists.
 *
 * - save(User user): Persists the provided User entity to the database. If the User already exists,
 *   its existing record will be updated; otherwise, a new record will be created. Returns the persisted User.
 *
 * TODO: Implement error handling in the repository layer for potential database exceptions.
 * TODO: Consider adding a method to check if a user with the same name or email already exists.
 */
public interface UserRepositoryI extends JpaRepository<User, Integer> {

    // Methods
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    User save(User user);
}
