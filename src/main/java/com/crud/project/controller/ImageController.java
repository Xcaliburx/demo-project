package com.crud.project.controller;

import com.crud.project.controller.util.DataControllerUtil;
import com.crud.project.model.Image;
import com.crud.project.response.ImageResponse;
import com.crud.project.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService service;

    @Autowired
    private DataControllerUtil util;

    @Operation(summary = "Upload New Image", description = "Upload new image to database")
    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Image image = service.saveImage(file);
            return new ResponseEntity<>(util.convertToImageResponse(image), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed upload file", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get Image Detail", description = "Get Image Detail Data")
    @GetMapping("/{id}")
    public ResponseEntity<ImageResponse> getImage(@PathVariable String id) {
        Image image = service.getImage(id);
        return new ResponseEntity<>(util.convertToImageResponse(image), HttpStatus.OK);
    }

    @Operation(summary = "Fetch Imager", description = "Get Image Data")
    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Image image = service.getImage(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
                .body(image.getData());
    }
}
