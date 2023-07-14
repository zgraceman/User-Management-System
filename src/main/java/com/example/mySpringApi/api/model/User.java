package com.example.mySpringApi.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * User is a data model that represents a user in the system.
 * Each User has an id, name, age, and email, which can be retrieved and updated.
 *
 * A User object's primary role in the application is to be managed by the
 * UserService, which provides various services for manipulating and accessing User data.
 * The UserController calls upon these services to handle incoming HTTP requests.
 *
 * Example usages of a User object include retrieving a user's data through a GET
 * request to the "/userById" or "/userByName" endpoints, where the UserService
 * retrieves a User from the underlying database.
 *
 * TODO: Replace getter, setter, and toString methods with Lombok annotations.
 *       For example, use @Getter, @Setter, and @ToString annotations from Lombok
 *       to automatically generate these methods.
 */
@Entity
public class User {

    // Fields
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int age;
    private String email;


    /**
     * Constructs a new User with the given parameters.
     *
     * @param id    the user's ID
     * @param name  the user's name
     * @param age   the user's age
     * @param email the user's email address
     */
    public User(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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
