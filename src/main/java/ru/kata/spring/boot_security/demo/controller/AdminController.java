package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;


    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin")
    public String index(Model model,
                        Authentication authentication) {
        model.addAttribute("user", userService.listUser());
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userA", userService.findUser(user.getId()));
        model.addAttribute("roles", roleService.findRoles());
        model.addAttribute("userC", new User());
        return "index";
    }

    @PostMapping("/admin/create")
    public String create(@ModelAttribute("userC") User user) {

        userService.createUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/edit")
    public String update(@ModelAttribute("user") User user) {

        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("admin/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
