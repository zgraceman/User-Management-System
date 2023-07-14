package com.example.mySpringApi.repository;

import com.example.mySpringApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryI extends JpaRepository<User, Integer> {
}
