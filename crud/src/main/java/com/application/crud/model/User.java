package com.application.crud.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
//import org.springframework.data.annotation.Id;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(String name, Integer age, String email, String password) {this(null, name, age, email, password);}

    public User(Long id, String name, Integer age, String email, String password) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    // Геттер для доступа к коллекции ролей
    public Set<Role> getRoles() {
        return roles;
    }
    public List<String> getRoleNames() {
        return roles.stream()
                .map(Role::getRole)
                .collect(Collectors.toList());
    }
    // Метод для получения ID текущих ролей (для чекбоксов)
    public List<Long> getRoleIds() {
        return this.roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return email; // Security использует email как username
    }

    public void addRole(String role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(new Role(role));
    }

    public String getRolesString() {
        return roles.stream().map(Role::getRole).collect(Collectors.joining(", "));
    }

    // Переопределение метода toString для удобного вывода информации о пользователе
    @Override
    public String toString() {
        return "id=" + id +
                ", name=" + name +
                ", age=" + age +
                ", email=" + email +
                ", roles=" + getRolesString();

    }
}
