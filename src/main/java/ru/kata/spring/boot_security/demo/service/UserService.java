package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> listUser();
    void saveUser(User user);
    void deleteUser(Long id);
    User findUser(Long id);
    void createUser(User user);
    User findByUsername(String name);
}
