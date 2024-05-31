package com.example.practica2.user;

import com.example.practica2.mock.Mock;
import com.example.practica2.project.Project;
import com.example.practica2.security.Rol;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class UserRequest {

    private String username;
    private String name;
    private String password;
    private boolean active = true;
    private List<Rol> roles;
    private List<Project> projects;
    public List<Rol> getRoles(){
        return roles == null ? null : new ArrayList<>(roles);
    }
    public List<Project> getProjects(){
        return projects == null ? null : new ArrayList<>(projects);
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
}
