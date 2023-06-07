package com.crud.project.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProjectResponse {
    private String projectName;
    private String description;
    private int price;
}
