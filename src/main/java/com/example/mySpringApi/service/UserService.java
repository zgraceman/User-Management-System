package com.example.mySpringApi.service;

import com.example.mySpringApi.api.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private List<User> userList;

    public UserService() {
        userList = new ArrayList<>();

        User user1 = new User(1, "Zach", 23, "zach@gmail.com");
        User user2 = new User(2, "Don", 33, "don@gmail.com");
        User user3 = new User(3, "Michelle", 20, "michelle@gmail.com");
        User user4 = new User(4, "Janet", 53, "janet@gmail.com");
        User user5 = new User(5, "Peter", 46, "peter@gmail.com");

        userList.addAll(Arrays.asList(user1, user2, user3, user4, user5));
    }

    public Optional<User> getUser(Integer id) {

        System.out.println("getUser Integer id Method");

        Optional optional = Optional.empty();

        for (User user: userList) {
            if(id == user.getId()) {
                optional = Optional.of(user);
                return optional;
            }
        }
        return optional;
    }

    public Optional<User> getUser(String name) {

        System.out.println("getUser String name Method");

        Optional optional = Optional.empty();

        for (User user: userList) {
            System.out.println(name);
            System.out.println(user.getName());
            if(name.equals(user.getName())) {
                System.out.println("true");
                optional = Optional.of(user);
                return optional;
            }
            else {
                System.out.println("false");
            }
        }
        return optional;
    }



}
