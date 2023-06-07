package com.crud.project.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {

    private String projectName;
    private String description;
    private int price;
    private long developerId;
}
