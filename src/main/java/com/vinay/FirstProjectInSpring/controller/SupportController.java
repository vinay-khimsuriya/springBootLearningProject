package com.vinay.FirstProjectInSpring.controller;

import com.vinay.FirstProjectInSpring.dto.*;
import com.vinay.FirstProjectInSpring.services.SupportService;

import jakarta.validation.Valid;

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
            "success",
            HttpStatus.CREATED.value(),
            "Support agent registered successfully!",
            1,
            savedSupport
    );

    return new ResponseEntity<>(response, HttpStatus.CREATED);
}

@PostMapping("/login")
public ResponseEntity<ApiResponse<JwtSupportResponseDTO>> login(@RequestBody @Valid SupportLoginDTO dto) {
    JwtSupportResponseDTO jwtResponse = service.login(dto);

    ApiResponse<JwtSupportResponseDTO> response = new ApiResponse<>(
            "success",
            HttpStatus.OK.value(),
            "Login successful!",
            1,
            jwtResponse
    );
    return ResponseEntity.ok(response);
}
}

