package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    public void save(User user);
    public void update(User user, boolean isAdmin);
    public void delete(Long userId);
    public User findByUsername(String username);
    public List<User> findAll();
}