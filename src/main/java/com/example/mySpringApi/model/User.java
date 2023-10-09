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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


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

    @NotNull(message = "Name cannot be null.")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters.")
    private String name;

    @NotNull(message = "Email cannot be null.")
    @Email(message = "Invalid email format.")
    @Column(unique = true)
    private String email;

    @Min(value = 0, message = "Age must be positive.")
    @Max(value = 150, message = "Age value is unrealistic.")
    private int age;

    @NotNull(message = "Password cannot be null.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    @Pattern.List({
            @Pattern(regexp = "(?=.*[0-9]).+", message = "Password must contain at least one digit."),
            @Pattern(regexp = "(?=.*[a-z]).+", message = "Password must contain at least one lowercase character."),
            @Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must contain at least one uppercase character."),
            @Pattern(regexp = "(?=.*[!@#$%^&*+=?-]).+", message = "Password must contain at least one special character.")
    })
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
