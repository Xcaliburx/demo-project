package com.crud.project.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageResponse {

    private String id;
    private String name;
    private String url;
    private String type;
    private long size;
    private byte[] file;

    public ImageResponse(String id, String name, String url, String type, long size, byte[] file) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
        this.file = file;
    }
}
