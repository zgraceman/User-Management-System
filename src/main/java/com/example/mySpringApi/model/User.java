package com.example.mySpringApi.model;

import jakarta.persistence.*;
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
 * @Entity: Specifies that this class is a JPA entity.
 * @Getter: Lombok's annotation to generate getters for all fields.
 * @Setter: Lombok's annotation to generate setters for all fields.
 * @NoArgsConstructor: Lombok's annotation to generate a no-args constructor.
 * @Table(name="user_info"): Specifies the name of the database table to be used.
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

    // Helper Methods
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
