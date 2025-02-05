package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImp(UserDao userDao, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role 'ROLE_USER' not found"));
        if (!user.getRoles().contains(userRole)) {
            user.addRole(userRole);
        }
        userDao.save(user);
    }

    @Transactional
    @Override
    public void update(User user, boolean isAdmin) {
        User existingUser = userDao.findById(user.getId()).orElseThrow();

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(existingUser.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        Role userRole = roleRepository.findByRole("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("User role not found"));
        user.addRole(userRole);
        Role adminRole = roleRepository.findByRole("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Admin role not found"));
        if (isAdmin) {
            user.addRole(adminRole);
        } else {
            user.getRoles().remove(adminRole);
        }
        userDao.save(user);
    }

    @Transactional
    @Override
    public void delete(Long userId) {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        for (Role role : user.getRoles()) {
            role.getUsers().remove(user);
        }
        userDao.delete(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
