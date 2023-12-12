package com.example.mySpringApi.service;

import com.example.mySpringApi.model.Role;
import java.util.Set;

/**
 * RoleService interface provides a contract for managing and retrieving Role entities.
 *
 * This interface is to be implemented by services that interact with the RoleRepository
 * to perform operations related to roles, such as finding roles by their names.
 * It is used primarily in user management tasks where roles need to be assigned or validated.
 */
public interface RoleService {

    /**
     * Finds roles by their names.
     *
     * This method is intended to query the RoleRepository to retrieve Role entities
     * based on a set of role names. It is used in user management to assign the correct
     * Role entities to users.
     *
     * @param roleNames A set of role names to search for.
     * @return A set of Role entities corresponding to the given names.
     */
    Set<Role> findRolesByNames(Set<String> roleNames);
}