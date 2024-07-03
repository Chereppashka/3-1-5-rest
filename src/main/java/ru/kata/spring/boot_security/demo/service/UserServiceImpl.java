package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUser() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        Optional<User> optionalUser = userRepository.findUserByUsername(user.getName());
        if (optionalUser.isPresent()) {
            throw new UsernameNotFoundException("User already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User findUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(new User());
    }

    @Override
    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void updateUser(Long id, User user) {
        user.setId(id);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public User findByUsername(String name) {
        Optional<User> user = userRepository.findUserByUsername(name);
        return user.orElse(new User());
    }

    @Transactional(readOnly = true)
    public User getUserWithRoles(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.getRoles().size();
        }
        return user;
    }

}
