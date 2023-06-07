package com.crud.project.service;

import com.crud.project.model.Project;
import com.crud.project.request.ProjectRequest;

import java.util.List;

public interface ProjectService {

    void createProject(ProjectRequest projectRequest);

    List<Project> getProjectByDeveloperId(long developerId);

    Project getProjectById(long id);

    void deleteProject(long id);
}
