package com.vinay.FirstProjectInSpring.controller;

import com.vinay.FirstProjectInSpring.dto.*;
import com.vinay.FirstProjectInSpring.services.SupportService;
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
public ResponseEntity<ApiResponse<SupportResponseDTO>> register(@RequestBody SupportDTO dto) {
    try {
        SupportResponseDTO savedSupport = service.register(dto);

        ApiResponse<SupportResponseDTO> response = new ApiResponse<>(
                "success",
                HttpStatus.CREATED.value(),
                "Support agent registered successfully!",
                1,
                savedSupport
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    } catch (RuntimeException e) {
        ApiResponse<SupportResponseDTO> response = new ApiResponse<>(
                "error",
                HttpStatus.CONFLICT.value(),
                e.getMessage(), 
                0,
                null
        );

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}


 @PostMapping("/login")
public ResponseEntity<ApiResponse<JwtSupportResponseDTO>> login(@RequestBody SupportLoginDTO dto) {
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
