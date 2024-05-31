package com.example.practica2.mock;

import com.example.practica2.expiration.Expiration;
import com.example.practica2.project.Project;
import com.example.practica2.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
//@NoArgsConstructor
@Entity
//@Table(name = "mock", uniqueConstraints = { @UniqueConstraint(columnNames = { "path" }) })
@Table(name = "mock")
public class Mock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String method;
    private Integer code;
    private String contentType;
    private String contentEncode;
    @Column(columnDefinition = "VARCHAR(10000)")
    private String body;
    private String header;
    private String description;
    private int delay;
    private String name;
    private String path;
    private String type;
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "project_Id")
    private Project project;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "expiration_id")
    private Expiration expiration;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt= new Date();
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean enableJwt;
//    private String token;

    public Mock(String method, Integer code, String contentType, String contentEncode, String body, String header,
                String description, int delay, String name, String path, String type,
                Project project, Expiration expiration) {
        this.method = method;
        this.code = code;
        this.contentType = contentType;
        this.contentEncode = contentEncode;
        this.body = body;
        this.header = header;
        this.description = description;
        this.delay = delay;
        this.name = name;
        this.path = path;
        this.type = type;
        this.project = project;
        this.expiration = expiration;
    }

    public Mock() {
    }

//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }

    public Boolean getEnableJwt() {
        return enableJwt;
    }

    public void setEnableJwt(Boolean enableJwt) {
        this.enableJwt = enableJwt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentEncode() {
        return contentEncode;
    }

    public void setContentEncode(String contentEncode) {
        this.contentEncode = contentEncode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
    @JsonIgnore
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Expiration getExpiration() {
        return expiration;
    }

    public void setExpiration(Expiration expiration) {
        this.expiration = expiration;
    }
}
