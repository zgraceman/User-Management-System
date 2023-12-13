package com.example.mySpringApi.runner;

import com.example.mySpringApi.model.Role;
import com.example.mySpringApi.model.User;
import com.example.mySpringApi.repository.RoleRepository;
import com.example.mySpringApi.repository.UserRepository;
import com.example.mySpringApi.service.RoleService;
import com.example.mySpringApi.service.RoleServiceImpl;
import com.example.mySpringApi.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * This class serves as a CommandLineRunner for the application.
 * It is annotated with @Component to be automatically detected by the Spring framework
 * during classpath scanning and automatically instantiated as a bean in the application context.
 *
 * Upon startup, it populates the UserRepository with initial data.
 */
@Component
@Slf4j
public class MyCommandLineRunner implements CommandLineRunner {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public MyCommandLineRunner(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    void created() {
        log.info("==== MyCommandLineRunner Initialized ====");
    }

    @Override
    public void run(String... args) throws Exception {
        // Create roles if they do not exist
        createRoleIfNotFound(1, "ADMIN");
        createRoleIfNotFound(2, "USER");

        // Create default user and admin if they don't exist
        userService.createDefaultUserIfNotFound(
                "Default Admin",
                "admin@example.com",
                0, "admin",
                "ADMIN"
        );
        userService.createDefaultUserIfNotFound(
                "Default User",
                "user@example.com",
                0,
                "user",
                "USER"
        );
    }

    private void createRoleIfNotFound(int id, String name) {
        if (roleRepository.findByName(name).isEmpty()) {
            Role role = new Role();
            role.setId(id);
            role.setName(name);
            roleRepository.save(role);
        }
    }
}
