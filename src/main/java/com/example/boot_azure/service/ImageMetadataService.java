package com.example.boot_azure.service;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class ImageMetadataService {

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    @Value("${azure.blob-storage.connection-string}")
    private String connectionString;

    private BlobServiceClient blobServiceClient;

    @PostConstruct
    public void init(){
        blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }

    public String uploadImage(MultipartFile image, String caption) throws IOException {

        String blobFilename = image.getOriginalFilename();
        BlobClient blobClient = blobServiceClient
                .getBlobContainerClient(containerName)
                .getBlobClient(blobFilename);

        blobClient.upload(image.getInputStream(),image.getSize(),true);

        return "Image uploaded";
    }

    public String downloadImage(String name) throws IOException {

        BlobClient blobClient = blobServiceClient
                .getBlobContainerClient(containerName)
                .getBlobClient(name);

        BinaryData cas = blobClient.downloadContent();

        return "Image download";
    }

}
