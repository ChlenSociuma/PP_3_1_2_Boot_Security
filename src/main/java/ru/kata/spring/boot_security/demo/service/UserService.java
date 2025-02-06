package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    public void save(User user);
    public void update(User user);
    public void delete(Long userId);
    public List<Role> getListOfRoles(List<String> roles);
    public User getUserById(Long id);
    public User findByUsername(String username);
    public List<User> findAll();
}