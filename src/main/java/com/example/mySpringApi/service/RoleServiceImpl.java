package com.example.mySpringApi.service;

import com.example.mySpringApi.model.Role;
import com.example.mySpringApi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * RoleServiceImpl provides functionality to manage and retrieve Role entities.
 *
 * This service interacts with the RoleRepository to perform operations related to roles,
 * such as finding roles by their names. It's used primarily in user management tasks
 * where roles need to be assigned or validated.
 *
 * TODO: Create exceptions for Role similar to User Exceptions
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    /**
     * Constructs the RoleServiceImpl with a RoleRepository.
     *
     * @param roleRepository The repository for accessing role data.
     */
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Finds roles by their names.
     *
     * This method queries the RoleRepository to retrieve Role entities based on a set of role names.
     * It's used in user management to assign the correct Role entities to users.
     *
     * @param roleNames A set of role names to search for.
     * @return A set of Role entities corresponding to the given names.
     */
    public Set<Role> findRolesByNames(Set<String> roleNames) {
        return roleRepository.findByNameIn(roleNames);
    }
}
