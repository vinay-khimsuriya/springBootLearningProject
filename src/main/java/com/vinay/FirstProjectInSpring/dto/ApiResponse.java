package com.vinay.FirstProjectInSpring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
    private String status;
    private int statusCode;
    private String message;
    private int count;
    private T data;
    private Object errors;

     public ApiResponse(String status, int statusCode, String message, int count, T data) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.count = count;
        this.data = data;
    }
}
