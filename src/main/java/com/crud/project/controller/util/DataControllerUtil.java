package com.crud.project.controller.util;

import com.crud.project.model.Broker;
import com.crud.project.model.Developer;
import com.crud.project.model.Image;
import com.crud.project.model.Project;
import com.crud.project.response.BrokerResponse;
import com.crud.project.response.DeveloperResponse;
import com.crud.project.response.ImageResponse;
import com.crud.project.response.ProjectResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DataControllerUtil {

    public List<BrokerResponse> convertToResponseList(List<Broker> brokerList) {
        return brokerList.stream()
                .map(this::convertToBrokerResponse)
                .collect(Collectors.toList());
    }

    public BrokerResponse convertToBrokerResponse(Broker broker) {
        return BrokerResponse.builder()
                .id(broker.getId())
                .name(broker.getName())
                .phone(broker.getPhone())
                .description(broker.getDescription())
                .location(broker.getLocation())
                .image(Optional.ofNullable(broker.getImage())
                        .map(this::convertToImageResponse)
                        .orElse(null))
                .build();
    }

    public List<DeveloperResponse> convertToDeveloperResponseList(List<Developer> developers) {
        return developers.stream()
                .map(this::convertToDeveloperResponse)
                .collect(Collectors.toList());
    }

    public DeveloperResponse convertToDeveloperResponse(Developer developer) {
        return DeveloperResponse.builder()
                .id(developer.getId())
                .name(developer.getName())
                .fee(developer.getFee())
                .location(developer.getLocation())
                .image(Optional.ofNullable(developer.getImage())
                        .map(this::convertToImageResponse)
                        .orElse(null))
                .projects(convertToProjectResponseList(developer.getProjects()))
                .build();
    }

    public List<ProjectResponse> convertToProjectResponseList(List<Project> projects) {
        return projects.stream()
                .map(this::converToProjectResponse)
                .collect(Collectors.toList());
    }

    public ProjectResponse converToProjectResponse(Project project) {
        return ProjectResponse.builder()
                .projectName(project.getProjectName())
                .description(project.getDescription())
                .price(project.getPrice())
                .build();
    }

    public ImageResponse convertToImageResponse(Image image) {
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/image/file/")
                .path(image.getId())
                .toUriString();

        return ImageResponse.builder()
                .id(image.getId())
                .name(image.getName())
                .url(fileDownloadUri)
                .type(image.getType())
                .size(image.getData().length)
                .build();
    }
}
