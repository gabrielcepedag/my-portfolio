package com.example.practica2.mock;

public class MockResponse {
    private Long id;
    private String method;
    private Integer code;
    private String contentType;
    private String contentEncode;
    private String body;
    private String header;
    private String description;
    private int delay;
    private String name;
    private String path;
    private String projectName;
    private String expirationName;
    private Boolean enableJwt;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getEnableJwt() {
        return enableJwt;
    }

    public void setEnableJwt(Boolean enableJwt) {
        this.enableJwt = enableJwt;
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

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getExpirationName() {
        return expirationName;
    }

    public void setExpirationName(String expirationName) {
        this.expirationName = expirationName;
    }

}
