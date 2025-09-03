package com.vinay.FirstProjectInSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtSupportResponseDTO {
    private String token;
    private SupportResponseDTO support;
}
