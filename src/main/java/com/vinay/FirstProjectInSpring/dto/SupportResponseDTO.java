package com.vinay.FirstProjectInSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupportResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String designation;
    private Boolean availability;
    private String status;
}