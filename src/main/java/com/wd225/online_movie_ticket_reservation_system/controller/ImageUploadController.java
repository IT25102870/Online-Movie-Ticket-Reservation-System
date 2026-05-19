package com.wd225.online_movie_ticket_reservation_system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;

@RestController
@RequestMapping("/upload")
public class ImageUploadController {

    private static final String UPLOAD_DIR = "data/images/";

    @PostMapping("/movie-image")
    public ResponseEntity<String> uploadMovieImage(@RequestParam("file") MultipartFile file) {
        try {
            // Validate file type
            String originalName = file.getOriginalFilename();
            if (originalName == null || originalName.isEmpty()) {
                return ResponseEntity.badRequest().body("Error: No file selected.");
            }

            String lower = originalName.toLowerCase();
            if (!lower.endsWith(".jpg") && !lower.endsWith(".jpeg")
                    && !lower.endsWith(".png") && !lower.endsWith(".webp")) {
                return ResponseEntity.badRequest().body("Error: Only JPG, PNG, or WEBP images are allowed.");
            }

            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate a unique filename to prevent collisions
            String extension = originalName.substring(originalName.lastIndexOf('.'));
            String fileName  = "movie_" + System.currentTimeMillis() + extension;
            Path   filePath  = uploadPath.resolve(fileName);

            // Save the file
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Return the public URL path
            return ResponseEntity.ok("/images/" + fileName);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
