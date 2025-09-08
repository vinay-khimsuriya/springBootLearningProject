package com.vinay.FirstProjectInSpring.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String UPLOAD_DIR = "uploads/";

    public FileStorageService() throws IOException {
        Files.createDirectories(Paths.get(UPLOAD_DIR)); 
    }

    public FileInfo saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty!");
        }

        String uniqueName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + uniqueName);
        Files.copy(file.getInputStream(), path);

        return new FileInfo(uniqueName, path.toString());
    }

    public byte[] getFile(String path) throws IOException {
        Path filePath = Paths.get(path);
        if (!Files.exists(filePath)) {
            throw new RuntimeException("File not found");
        }
        return Files.readAllBytes(filePath);
    }

    // Replace the record with a regular class for Java 11 compatibility
    public static class FileInfo {
        private String fileName;
        private String filePath;

        public FileInfo(String fileName, String filePath) {
            this.fileName = fileName;
            this.filePath = filePath;
        }

        // Getters
        public String getFileName() {
            return fileName;
        }

        public String getFilePath() {
            return filePath;
        }

        // Setters (optional, if needed)
        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }
    }
}