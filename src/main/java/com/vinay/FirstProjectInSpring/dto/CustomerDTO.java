package com.vinay.FirstProjectInSpring.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces")
    @Size(max = 50, message = "Name can be up to 50 characters only")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Min(value = 0, message = "Age must be positive")
    private Integer age;

    @Length(max = 50, message = "Designation can be up to 50 characters only")
    private String designation;

    private String sex;

    @NotBlank(message = "Password is required")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
        message = "Password must be at least 8 characters, include uppercase, lowercase, number, and special character"
    )
    private String password;

    
    private String imageName;
    private String imagePath;
}
