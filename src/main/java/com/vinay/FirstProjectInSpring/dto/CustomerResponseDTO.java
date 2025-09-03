package com.vinay.FirstProjectInSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String mobileNumber;
    private Integer age;
    private String sex;
    private String imageName;
}
