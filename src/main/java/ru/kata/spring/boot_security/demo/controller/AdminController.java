package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String findAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", new User());
        return "admin";
    }

    @PostMapping("/admin/save")
    public String saveUser(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("admin/update")
    public String updateUser(@ModelAttribute User user, @RequestParam(defaultValue = "false") boolean isAdmin) {
        userService.update(user, isAdmin);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam("userId") Long userId) {
        userService.delete(userId);
        return "redirect:/admin";
    }
}
