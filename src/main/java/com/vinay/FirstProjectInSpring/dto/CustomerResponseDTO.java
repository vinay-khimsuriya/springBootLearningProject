package com.vinay.FirstProjectInSpring.dto;

import org.hibernate.validator.constraints.Length;

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
    private String designation;
    private String sex;
    private String imageName;
    private String imagePath;
}
