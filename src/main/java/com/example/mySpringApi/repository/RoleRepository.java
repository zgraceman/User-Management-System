package com.example.mySpringApi.repository;

import com.example.mySpringApi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

/**
 * RoleRepository is a JPA repository for the Role entity.
 * It provides a way to perform various operations on the role data in the database, such as
 * querying roles by name and finding roles within a set of names.
 * <p>
 * This interface extends JpaRepository, which provides standard CRUD operations and JPA query capabilities.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a role by its name.
     * This method is used to fetch a specific role from the database based on its name.
     *
     * @param name The name of the role to find.
     * @return An Optional containing the Role if found, or empty if not.
     */
    Optional<Role> findByName(String name);
    
    /**
     * Finds roles by a set of names.
     * This method is used to fetch multiple roles from the database based on a set of role names.
     *
     * @param names The set of role names to find.
     * @return A set of Roles corresponding to the given names.
     */
    Set<Role> findByNameIn(Set<String> names);
}
