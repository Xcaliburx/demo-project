package com.crud.project.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DeveloperResponse {
    private long id;
    private String name;
    private String location;
    private int fee;
    private ImageResponse image;
    private List<ProjectResponse> projects;
}
