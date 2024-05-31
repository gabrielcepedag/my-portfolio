package com.example.practica2.project;

import com.example.practica2.mock.Mock;
import com.example.practica2.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "project")
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Mock> mocks;

    public Project(String name) {
        this.name = name;
    }

    public Project() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Mock> getMocks() {
        return mocks == null? new ArrayList<>() : mocks;
    }

    public void setMocks(List<Mock> mocks) {
        this.mocks = mocks;
    }
}
