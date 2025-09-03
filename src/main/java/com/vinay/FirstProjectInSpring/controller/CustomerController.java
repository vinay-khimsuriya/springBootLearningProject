package com.vinay.FirstProjectInSpring.controller;

import com.vinay.FirstProjectInSpring.dto.*;
import com.vinay.FirstProjectInSpring.model.Customer;
import com.vinay.FirstProjectInSpring.security.JwtUtil;
import com.vinay.FirstProjectInSpring.services.CustomerService;

import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService service;
   private final JwtUtil jwtUtil;
  

    public CustomerController(CustomerService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil =jwtUtil;
    }

 @GetMapping("/{id}/image")
 @Transactional(readOnly = true) 
public ResponseEntity<ApiResponse<byte[]>> getCustomerImage(
        @PathVariable Long id,
        @RequestHeader(value = "Authorization", required = false) String authHeader) {

    // 🔒 Check Authorization header
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        ApiResponse<byte[]> response = new ApiResponse<>(
                "error",
                HttpStatus.UNAUTHORIZED.value(),
                "Missing or invalid Authorization header",
                0,
                null
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    try {
        String token = authHeader.substring(7);

       
        String email = jwtUtil.extractEmail(token);
        String role = jwtUtil.extractRole(token);

        Customer customer = service.getCustomerById(id);

        if (!customer.getEmail().equals(email) && !"ADMIN".equals(role) && !"SUPPORT".equals(role)) {
            ApiResponse<byte[]> response = new ApiResponse<>(
                    "error",
                    HttpStatus.FORBIDDEN.value(),
                    "You are not authorized to view this image",
                    0,
                    null
            );
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        if (customer.getImage() == null) {
            ApiResponse<byte[]> response = new ApiResponse<>(
                    "error",
                    HttpStatus.NOT_FOUND.value(),
                    "No image found for this customer",
                    0,
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<byte[]> response = new ApiResponse<>(
                "success",
                HttpStatus.OK.value(),
                "Image fetched successfully",
                1,
                customer.getImage()
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" +
                        (customer.getImageName() != null ? customer.getImageName() : "image.jpg") + "\"")
                .body(response);

    } catch (RuntimeException ex) {
        ApiResponse<byte[]> response = new ApiResponse<>(
                "error",
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                0,
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    } catch (Exception ex) {
        ApiResponse<byte[]> response = new ApiResponse<>(
                "error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred",
                0,
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}



    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
        public ResponseEntity<ApiResponse<CustomerResponseDTO>> register(@ModelAttribute @Valid CustomerDTO dto) {
            try {
                CustomerResponseDTO savedCustomer = service.register(dto);

                ApiResponse<CustomerResponseDTO> response = new ApiResponse<>(
                    "success",
                    HttpStatus.CREATED.value(),
                    "Customer registered successfully!",
                    1,
                    savedCustomer
                );

                return new ResponseEntity<>(response, HttpStatus.CREATED);

            } catch (RuntimeException e) {
                ApiResponse<CustomerResponseDTO> response = new ApiResponse<>(
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
            } 
            catch (RuntimeException e) {
                ApiResponse<JwtResponseDTO> response = new ApiResponse<>(
                    "error",
                    HttpStatus.UNAUTHORIZED.value(),
                    e.getMessage(),
                    0,
                    null
                );
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        }
}
