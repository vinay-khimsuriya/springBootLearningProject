package com.vinay.FirstProjectInSpring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z ]+$")
    @Length(max = 50)
    private String name;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$")
    private String mobileNumber;

    @Min(0)
    private Integer age;

    private String sex;

    @NotBlank
    private String password;

    private String imageName; 
    private String imagePath; 
}
