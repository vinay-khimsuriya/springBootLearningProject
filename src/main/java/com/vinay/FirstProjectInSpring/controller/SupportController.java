
package com.vinay.FirstProjectInSpring.controller;

import com.vinay.FirstProjectInSpring.dto.*;
import com.vinay.FirstProjectInSpring.services.SupportService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/support")
public class SupportController {
    private final SupportService service;

    public SupportController(SupportService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<SupportResponseDTO>> register(@RequestBody @Valid SupportDTO dto) {
        SupportResponseDTO savedSupport = service.register(dto);
        ApiResponse<SupportResponseDTO> response = new ApiResponse<>(
                "success", 201, "Support agent registered successfully!", 1, savedSupport
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtSupportResponseDTO>> login(@RequestBody @Valid SupportLoginDTO dto) {
        JwtSupportResponseDTO jwtResponse = service.login(dto);
        ApiResponse<JwtSupportResponseDTO> response = new ApiResponse<>(
                "success", 200, "Login successful!", 1, jwtResponse
        );
        return ResponseEntity.ok(response);
    }

    // Update online/offline status manually
    @PostMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam boolean status) {
        service.updateStatus(id, status);
        return ResponseEntity.ok("Status updated to " + (status ? "online" : "offline"));
    }

    // Update availability manually
    @PostMapping("/{id}/availability")
    public ResponseEntity<String> updateAvailability(@PathVariable Long id, @RequestParam boolean available) {
        service.updateAvailability(id, available);
        return ResponseEntity.ok("Availability updated to " + available);
    }

    // Get all available supports
    @GetMapping("/available")
    public List<SupportResponseDTO> getAvailableSupports() {
        return service.getAvailableSupports();
    }
}

