package com.example.boot_azure.controller;

import com.example.boot_azure.service.ImageMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImageMetadataController {

    @Autowired
    private ImageMetadataService imageMetadataService;

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile image, @RequestParam("caption") String caption) throws IOException {

        return imageMetadataService.uploadImage(image,caption);
    }

    @PostMapping("/download")
    public String downloadImage( @RequestParam("name") String name) throws IOException {

        return imageMetadataService.downloadImage(name);
    }

}
