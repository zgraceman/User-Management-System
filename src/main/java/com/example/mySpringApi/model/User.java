package com.example.mySpringApi.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

/**
 * The User is a JPA entity that represents a user in the application.
 * It includes fields for id, name, email, age, password, and roles. This entity is managed
 * by UserService for various operations such as retrieval, update, and deletion.
 * <p>
 * This entity is mapped to the "user_info" table in the database.
 * <p>
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

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

    public void setPassword(String rawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(rawPassword);
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
