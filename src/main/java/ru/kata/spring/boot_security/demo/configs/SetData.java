package ru.kata.spring.boot_security.demo.configs;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class SetData {

    private final UserService userService;
    private final RoleService roleService;

    public SetData(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void initiation() {

        Set<Role> setRoles = new HashSet<>();
        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");
        setRoles.add(adminRole);
        setRoles.add(userRole);
        roleService.createRoles(setRoles);

        User admin = new User("admin", "admin", 30, "admin");
        admin.setRoles(setRoles);
        userService.createUser(admin);

        User user = new User("user", "user", 20, "user");
        user.setRoles(Set.of(userRole));
        userService.createUser(user);
    }
}
