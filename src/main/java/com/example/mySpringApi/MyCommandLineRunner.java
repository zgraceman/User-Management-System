package com.example.mySpringApi;

import com.example.mySpringApi.model.User;
import com.example.mySpringApi.repository.UserRepositoryI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

    private UserRepositoryI userRepositoryI;

    /**
     * Constructor to inject UserRepositoryI bean into MyCommandLineRunner.
     *
     * @param userRepositoryI User repository bean.
     */
    @Autowired
    public MyCommandLineRunner(UserRepositoryI userRepositoryI) {
        this.userRepositoryI = userRepositoryI;
    }

    /**
     * Populate the UserRepository with initial data.
     *
     * @param args Command line arguments.
     * @throws Exception If an error occurs during execution.
     */
    @Override
    public void run(String... args) throws Exception {

        userRepositoryI.save(new User(1, "Zach", 23, "zach@gmail.com"));
        userRepositoryI.save(new User(2, "Don", 33, "don@gmail.com"));
        userRepositoryI.save(new User(3, "Michelle", 20, "michelle@gmail.com"));
        userRepositoryI.save(new User(4, "Janet", 53, "janet@gmail.com"));
        userRepositoryI.save(new User(5, "Peter", 46, "peter@gmail.com"));

    }
}
