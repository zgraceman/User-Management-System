package com.example.mySpringApi.repository;

import com.example.mySpringApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryI extends JpaRepository<User, Integer> {

    Optional<User> findByName(String name);
}
