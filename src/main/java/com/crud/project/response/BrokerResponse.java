package com.crud.project.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BrokerResponse {
    private long id;
    private String name;
    private String description;
    private String phone;
    private String location;
    private ImageResponse image;
}
