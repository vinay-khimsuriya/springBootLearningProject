package com.vinay.FirstProjectInSpring.dto;


import lombok.Data;

@Data
public class LoginResponseDTO {
    private boolean status;
    private int statusCode;
    private String message;
    private Object data;
}
