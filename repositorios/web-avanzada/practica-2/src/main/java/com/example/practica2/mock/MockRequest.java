package com.example.practica2.mock;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MockRequest {
    @NotBlank
    private String method;
    @Positive
    private Integer code;
    private String contentType;
    private String contentEncode;
    private String body;
    private List<HeaderRequest> header;
    private String description;
    private int delay;
    private String name;
    @NotBlank
    private String path;
    @NotNull
    private UUID project_id;
    @Positive
    private Long expiration_id;
    private Boolean enableJwt;

    public Boolean getEnableJwt() {
        return enableJwt;
    }

    public void setEnableJwt(Boolean enableJwt) {
        this.enableJwt = enableJwt;
    }

    public List<HeaderRequest> getHeader() {
        return header;
    }

    public void setHeaderRequests(List<HeaderRequest> header) {
        this.header = header;
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

//    public String getHeader() {
//        return header;
//    }
//
//    public void setHeader(String header) {
//        this.header = header;
//    }

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

//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }

    public UUID getProject_id() {
        return project_id;
    }

    public void setProjectId(UUID project_id) {
        this.project_id = project_id;
    }

    public Long getExpiration_id() {
        return expiration_id;
    }

    public void setExpirationId(Long expiration_id) {
        this.expiration_id = expiration_id;
    }
}
