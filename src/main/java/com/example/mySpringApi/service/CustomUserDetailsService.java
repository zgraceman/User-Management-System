package com.example.mySpringApi.service;

import com.example.mySpringApi.model.User;
import com.example.mySpringApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CustomUserDetailsService implements the UserDetailsService interface to provide
 * a custom way of fetching user details.
 *
 * This service is used by Spring Security to perform authentication and authorization
 * by loading user-specific data.
 *
 * Methods:
 * - loadUserByUsername(String email): Overrides the method from UserDetailsService
 *   to load the user by email instead of a username.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructs the CustomUserDetailsService with a UserRepository.
     *
     * @param userRepository The repository for accessing user data.
     */
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by their email address.
     *
     * This method is used by Spring Security to fetch user details required for authentication.
     * It converts the roles of the user to GrantedAuthority objects for role-based security.
     *
     * @param email The email of the user to load.
     * @return UserDetails object containing user data and authorities.
     * @throws UsernameNotFoundException if the user is not found with the provided email.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Convert Roles to GrantedAuthority
        Set<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                grantedAuthorities);
    }
}