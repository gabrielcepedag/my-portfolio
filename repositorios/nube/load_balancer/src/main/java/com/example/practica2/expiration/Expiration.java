package com.example.practica2.expiration;

import com.example.practica2.mock.Mock;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "expiration")
public class Expiration implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int value;
    @OneToMany(mappedBy = "expiration", fetch = FetchType.LAZY)
    private List<Mock> projects;

    public Expiration(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public Expiration() {
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @JsonIgnore
    public List<Mock> getProjects() {
        return projects;
    }

    public void setProjects(List<Mock> projects) {
        this.projects = projects;
    }
}
