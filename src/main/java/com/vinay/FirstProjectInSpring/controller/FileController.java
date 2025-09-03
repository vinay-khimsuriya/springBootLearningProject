package com.vinay.FirstProjectInSpring.controller;

import com.vinay.FirstProjectInSpring.dto.ApiResponse;
import com.vinay.FirstProjectInSpring.services.FileStorageService;
import com.vinay.FirstProjectInSpring.services.FileStorageService.FileInfo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    // Upload any file
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<?>> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>(
                "error",
                HttpStatus.BAD_REQUEST.value(),
                "No file provided or file is empty",
                0,
                null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            FileInfo fileInfo = fileStorageService.saveFile(file);
            ApiResponse<FileInfo> response = new ApiResponse<>(
                "success",
                HttpStatus.OK.value(),
                "File uploaded successfully",
                1,
                fileInfo
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the exception (optional)
            e.printStackTrace();

            ApiResponse<String> response = new ApiResponse<>(
                "error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "File upload failed: " + e.getMessage(),
                0,
                null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Download file by path
    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(@RequestParam String path) {
        if (path == null || path.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>(
                "error",
                HttpStatus.BAD_REQUEST.value(),
                "File path is missing",
                0,
                null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            byte[] data = fileStorageService.getFile(path);

            // Extract filename from path
            String fileName = path.contains("/") ? path.substring(path.lastIndexOf("/") + 1) : path;

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(data);

        } catch (Exception e) {
            e.printStackTrace();

            ApiResponse<String> response = new ApiResponse<>(
                "error",
                HttpStatus.NOT_FOUND.value(),
                "File download failed: " + e.getMessage(),
                0,
                null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
