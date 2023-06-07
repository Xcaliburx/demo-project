package com.crud.project.controller;

import com.crud.project.controller.util.DataControllerUtil;
import com.crud.project.model.Developer;
import com.crud.project.request.DeveloperRequest;
import com.crud.project.response.DeveloperResponse;
import com.crud.project.service.DataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/developer")
@Tag(name = "Developer Controller")
@PreAuthorize("hasAuthority('Developer')")
public class DeveloperController {

    @Autowired
    DataService dataService;

    @Autowired
    DataControllerUtil util;

    @Operation(summary = "Add Developer", description = "Add New Developer Data")
    @PostMapping
    public ResponseEntity<?> addDeveloper(@RequestBody DeveloperRequest request) {
        dataService.addDeveloper(request);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @Operation(summary = "Get All Developer", description = "Get All Developers Data")
    @GetMapping
    public ResponseEntity<List<DeveloperResponse>> getDevelopers() {
        List<Developer> developers = dataService.getDevelopers();
        return new ResponseEntity<>(util.convertToDeveloperResponseList(developers), HttpStatus.OK);
    }

    @Operation(summary = "Get Developer By Id", description = "Get Developer Data By Id")
    @GetMapping("/{id}")
    public ResponseEntity<DeveloperResponse> getDeveloperById(@PathVariable long id) {
        Developer developer = dataService.getDeveloperById(id);
        return new ResponseEntity<>(util.convertToDeveloperResponse(developer), HttpStatus.OK);
    }

    @Operation(summary = "Update Developer", description = "Update Developer Data by Id")
    @PostMapping("/{id}")
    public ResponseEntity<?> updateDeveloper(
            @PathVariable long id,
            @RequestBody DeveloperRequest request) throws IOException {
        dataService.updateDeveloper(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete Developer", description = "Delete Developer By Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeveloper(@PathVariable long id) {
        dataService.deleteDeveloper(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
