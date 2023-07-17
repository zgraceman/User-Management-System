package com.example.mySpringApi;

import com.example.mySpringApi.model.User;
import com.example.mySpringApi.repository.UserRepositoryI;
import jakarta.annotation.PostConstruct;
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
     * Logs a message after the MyCommandLineRunner bean has been fully initialized.
     *
     * The @PostConstruct annotation indicates that this method should be executed after
     * dependency injection is done to perform any necessary initialization.
     * This method is called once, just after the initialization of bean properties
     * and the context.
     *
     * The logged message serves as a confirmation that the MyCommandLineRunner bean
     * has been created and initialized properly. This can be useful in understanding
     * the lifecycle of this component within the application context.
     */
    @PostConstruct
    void created() {
        log.info("==================== MyCommandLineRunner Got Created ====================");
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
