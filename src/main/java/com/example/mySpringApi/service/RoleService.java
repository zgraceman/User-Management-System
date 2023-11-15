package com.example.mySpringApi.service;

import com.example.mySpringApi.model.Role;
import com.example.mySpringApi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> findRolesByNames(Set<String> roleNames) {
        return roleRepository.findByNameIn(roleNames);
    }
}
