package com.crud.project.controller;

import com.crud.project.controller.util.DataControllerUtil;
import com.crud.project.model.Project;
import com.crud.project.request.ProjectRequest;
import com.crud.project.response.ProjectResponse;
import com.crud.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Project Controller")
@PreAuthorize("hasAuthority('Developer')")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    DataControllerUtil util;

    @Operation(summary = "Create Project", description = "Create New Project")
    @PostMapping("/project")
    public ResponseEntity<?> createProject(@RequestBody ProjectRequest request) {
        projectService.createProject(request);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Projects By Developer Id", description = "Get All Project List of a Developer using Id")
    @GetMapping("/{projectId}/project")
    public ResponseEntity<List<ProjectResponse>> getProjectByDeveloperId(@PathVariable long projectId) {
        List<Project> responses = projectService.getProjectByDeveloperId(projectId);
        return new ResponseEntity<>(util.convertToProjectResponseList(responses), HttpStatus.OK);
    }

    @Operation(summary = "Get Project By Id", description = "Get Project Data By Id")
    @GetMapping("/project/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable long id) {
        Project project = projectService.getProjectById(id);
        return new ResponseEntity<>(util.converToProjectResponse(project), HttpStatus.OK);
    }

    @Operation(summary = "Delete Project", description = "Delete Project By Id")
    @DeleteMapping("/project/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable long id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
