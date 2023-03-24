package com.github.frankiie.springboot.controllers.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.frankiie.springboot.domain.collection_image.entity.Image;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class FileUploadController {
  
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
    // Check if the file size is less than or equal to 10MB
    if (file.getSize() > 10 * 1024 * 1024) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File size must be less than or equal to 10MB.");
    }

    // Check if the file type is allowed
    String contentType = file.getContentType();
    if (!contentType.equals("application/pdf") && !contentType.equals("text/plain")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only PDF and TXT files are allowed.");
    }

    try {
        // Save the uploaded file to your server
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get("uploads/" + fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        // Save information about the uploaded file to your database
        Image fileUpload = new Image();
        // fileUpload.setTitle(fileName);
        // fileUpload.setSize(file.getSize());
        // fileUpload.setType(contentType);
        return ResponseEntity.ok().body("File uploaded successfully.");

    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
    }
}
}
