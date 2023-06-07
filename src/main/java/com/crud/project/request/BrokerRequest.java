package com.crud.project.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrokerRequest {

    private String name;
    private String description;
    private String phone;
    private String location;
    private MultipartFile file;
}
