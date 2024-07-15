package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    void addUser(User user);
    void updateUser(User user);
    void removeUser(long id);
    User getUserById(long id);
    List<User> getListUsers();
    User findByFirstName(String firstName);
}
