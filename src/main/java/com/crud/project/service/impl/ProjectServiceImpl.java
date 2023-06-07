package com.crud.project.service.impl;

import com.crud.project.model.Project;
import com.crud.project.repository.DeveloperRepository;
import com.crud.project.repository.ProjectRepository;
import com.crud.project.request.ProjectRequest;
import com.crud.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Override
    public void createProject(ProjectRequest projectRequest) {
        Project project = new Project();
        project.setProjectName(projectRequest.getProjectName());
        project.setDescription(projectRequest.getDescription());
        project.setPrice(projectRequest.getPrice());
        if (developerRepository.findById(projectRequest.getDeveloperId()).isPresent()) {
            project.setDeveloper(developerRepository.findById(projectRequest.getDeveloperId()).get());
        }
        repository.save(project);
    }

    @Override
    public List<Project> getProjectByDeveloperId(long developerId) {
        return repository.getProjectsByDeveloper_id(developerId);
    }

    @Override
    public Project getProjectById(long id) {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        }
        return null;
    }

    @Override
    public void deleteProject(long id) {
        repository.deleteById(id);
    }
}
