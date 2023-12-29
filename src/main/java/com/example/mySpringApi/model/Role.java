package com.example.mySpringApi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Role entity represents a role or authority that can be assigned to a user in the application.
 * It maps to the "role" table in the database and is used to implement role-based access control (RBAC).
 * <p>
 * Annotations:
 * - @Entity: Specifies that this class is an entity and is mapped to a database table.
 * - @Getter, @Setter: Lombok annotations to automatically generate getter and setter methods for the fields.
 * - @NoArgsConstructor: Lombok annotation to generate a no-argument constructor.
 * - @Table(name = "role"): Specifies the name of the table in the database for this entity.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
}
