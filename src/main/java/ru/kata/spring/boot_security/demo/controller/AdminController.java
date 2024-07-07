package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.validator.UserValidator;

import javax.validation.Valid;

@Controller
public class AdminController {

    private final UserValidator userValidator;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    @Autowired
    public AdminController(UserValidator userValidator, UserService userService, RoleService roleService, PasswordEncoder encoder) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.roleService = roleService;
        this.encoder = encoder;
    }


    @GetMapping("/admin")
    public String index(Model model,
                        Authentication authentication) {
        model.addAttribute("users", userService.listUser());
        User user = (User) authentication.getPrincipal();
        model.addAttribute("userA", userService.findUser(user.getId()));
        model.addAttribute("userC", new User());
        return "index";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("userC") @Valid User user, BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "index";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userService.createUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("admin/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "index";
        }

        userService.saveUser(user);
        return "redirect:/admin";
    }

}
