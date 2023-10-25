package com.example.mySpringApi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.*;

/**
 * Represents an address within the user management system.
 *
 * This JPA entity maps to the "address" table in the database, and it is intended to store
 * address details for users. Each address is unique to a user, establishing a one-to-one
 * relationship between the User and Address entities.
 *
 * The class leverages Lombok to automatically generate boilerplate code like getters,
 * setters, constructors, and the toString method. Annotations from Jakarta Persistence API
 * are used to define the object-relational mapping.
 *
 * @see User
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    /**
     * Provides a string representation of the address, excluding the unique identifier.
     *
     * @return a string representation of the address
     */
    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
