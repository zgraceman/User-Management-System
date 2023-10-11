package com.example.mySpringApi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The User is a JPA entity that represents a user in the application.
 * It includes fields for id, name, age, and email. This entity is managed
 * by UserService for various operations such as retrieval, update, and deletion.
 *
 * This entity is mapped to the "user_info" table in the database.
 *
 * Annotations:
 * This class is specified as a JPA entity using the @Entity annotation.
 * Lombok's @Getter annotation used to generate getters for all fields.
 * Lombok's @Setter annotation used to generate setters for all fields.
 * Lombok's @NoArgsConstructor annotation used to generate a no-args constructor.
 * This class specifies the name of the database table to be used with @Table(name="user_info").
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="user_info")
public class User {

    // Fields
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(unique = true)
    private String email;

    private int age;

    private String password;

    // Constructor

    /**
     * Constructs a new User with the given parameters.
     *
     * @param name  the user's name
     * @param age   the user's age
     * @param email the user's email address
     */
    public User(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    /**
     * Sets the user's raw password, then hashes and stores it using BCrypt.
     *
     * @param encryptedPassword the plain-text password provided by the user or application
     */
    public void setPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

    /**
     * Helper method to represent the User object as a string. Excludes sensitive information such as password.
     *
     * @return a string representation of the User object
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
