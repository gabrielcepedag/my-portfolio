package com.example.practica2.project;

import com.example.practica2.mock.MockResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProjectResponse {
    private UUID id;
    private String name;
    private Long userId;
    private List<MockResponse> mocks;

    public ProjectResponse() {
    }

    public ProjectResponse(UUID id, String name, Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    public List<MockResponse> getMocks() {
        return mocks == null ? new ArrayList<>() : new ArrayList<>(mocks);
    }

    public void setMockResponseList(List<MockResponse> mocks) {
        this.mocks = mocks;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
