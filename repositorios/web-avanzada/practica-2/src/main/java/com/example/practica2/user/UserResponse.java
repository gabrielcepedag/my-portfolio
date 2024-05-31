package com.example.practica2.user;

import com.example.practica2.mock.Mock;
import com.example.practica2.project.Project;
import com.example.practica2.security.Rol;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL) //Oculta los valores null del json
public class UserResponse {
    private Long id;
    private String username;
    private String name;
    private boolean active;
    private List<Rol> roles;
    private List<Project> projects;
    public List<Rol> getRoles(){
        return roles == null ? new ArrayList<>() : new ArrayList<>(roles);
    }
    public List<Project> getProjects(){
        return projects == null ? new ArrayList<>() : new ArrayList<>(projects);
    }
    public void setRoles(List<Rol> rols) {
        if (rols == null) {
            this.roles = null;
        } else {
            this.roles = Collections.unmodifiableList(rols);
        }
    }
    public void setProjects(List<Project> projects) {
        if (projects == null) {
            this.projects = null;
        } else {
            this.projects = Collections.unmodifiableList(projects);
        }
    }

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
