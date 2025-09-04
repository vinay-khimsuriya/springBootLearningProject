package com.vinay.FirstProjectInSpring.exception;

import com.vinay.FirstProjectInSpring.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ---------------- Validation Errors ----------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiResponse<Void> response = new ApiResponse<>(
                "error",
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                errors.size(),
                null
        );
        response.setErrors(errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // ---------------- Business Logic / Runtime Errors ----------------
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException ex) {
        ApiResponse<Void> response = new ApiResponse<>(
                "error",
                HttpStatus.BAD_REQUEST.value(),
                "Business rule failed",
                1,
                null
        );
        response.setErrors(Map.of("error", ex.getMessage())); 
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // ---------------- General Exception ----------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        ApiResponse<Void> response = new ApiResponse<>(
                "error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unexpected error",
                1,
                null
        );
        response.setErrors(Map.of("error", ex.getMessage())); 
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
