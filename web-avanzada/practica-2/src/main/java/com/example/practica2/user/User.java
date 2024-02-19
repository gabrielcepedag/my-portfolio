package com.example.practica2.user;

import com.example.practica2.project.Project;
import com.example.practica2.security.Rol;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String name;
    private String password;
    @Column(columnDefinition = "boolean default TRUE")
    private boolean active;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Rol> roles;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Project> projects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public List<Project> getProjects() {
        return projects == null ? new ArrayList<>() : projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

}
