package com.vinay.FirstProjectInSpring.controller;

import com.vinay.FirstProjectInSpring.dto.*;
import com.vinay.FirstProjectInSpring.services.CustomerService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    // ---------------- Registration ----------------
    @PostMapping("/register")
public ResponseEntity<ApiResponse<CustomerResponseDTO>> register(
        @RequestBody @Valid CustomerDTO dto) {

    CustomerResponseDTO savedCustomer = service.register(dto);

    ApiResponse<CustomerResponseDTO> response = new ApiResponse<>(
            "success",
            HttpStatus.CREATED.value(),
            "Customer registered successfully!",
            1,
            savedCustomer
    );

    return new ResponseEntity<>(response, HttpStatus.CREATED);
}

    // ---------------- Login ----------------
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponseDTO>> login(@RequestBody CustomerLoginDTO dto) {
        try {
            JwtResponseDTO jwtResponse = service.login(dto);
            ApiResponse<JwtResponseDTO> response = new ApiResponse<>(
                    "success",
                    HttpStatus.OK.value(),
                    "Login successful!",
                    1,
                    jwtResponse
            );
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            ApiResponse<JwtResponseDTO> response = new ApiResponse<>(
                    "error",
                    HttpStatus.UNAUTHORIZED.value(),
                    e.getMessage(),
                    0,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            ApiResponse<JwtResponseDTO> response = new ApiResponse<>(
                    "error",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unexpected error: " + e.getMessage(),
                    0,
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
