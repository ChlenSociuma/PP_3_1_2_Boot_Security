package ru.kata.spring.boot_security.demo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String findAllUsers(Model model, Principal principal) {
        User currentUser = userService.findByUsername(principal.getName());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", currentUser);
        return "admin";
    }

    @PostMapping("/admin/save")
    public String saveUser(@ModelAttribute @Valid User user,
                           @RequestParam("roles") List<String> roles
    ) {
        user.setRoles(userService.getListOfRoles(roles));
        userService.save(user);

        return "redirect:/admin";
    }

    @PostMapping("/admin/update")
    public String updateUser(@ModelAttribute User user, @RequestParam("roles") List<String> roles) {
        user.setRoles(userService.getListOfRoles(roles));
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam("userId") Long userId) {
        userService.delete(userId);
        return "redirect:/admin";
    }
}
