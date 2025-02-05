package ru.kata.spring.boot_security.demo.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String role;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role() {}

    public Role(String role) { this.role = role; }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public List<User> getUsers() { return users; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return id == role1.id && Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }

    @Override
    public String toString() {
        return role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
