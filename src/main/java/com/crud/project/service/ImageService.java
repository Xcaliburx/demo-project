package com.crud.project.service;

import com.crud.project.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    Image saveImage(MultipartFile file) throws IOException;

    Image getImage(String id);
}
