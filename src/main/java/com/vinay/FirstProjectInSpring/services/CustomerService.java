package com.vinay.FirstProjectInSpring.services;

import com.vinay.FirstProjectInSpring.dto.*;
import com.vinay.FirstProjectInSpring.model.Customer;
import com.vinay.FirstProjectInSpring.repository.CustomerRepository;
import com.vinay.FirstProjectInSpring.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public CustomerService(CustomerRepository customerRepository, JwtUtil jwtUtil) {
        this.customerRepository = customerRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public CustomerResponseDTO register(CustomerDTO dto) {
        if (customerRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Customer with this email already exists!");
        }

        Customer c = new Customer();
        c.setName(dto.getName());
        c.setEmail(dto.getEmail());
        c.setMobileNumber(dto.getMobileNumber());
        c.setAge(dto.getAge());
        c.setSex(dto.getSex());
        c.setPassword(encoder.encode(dto.getPassword()));

        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            if (dto.getImage().getSize() > 3 * 1024 * 1024) { // 3MB
                throw new RuntimeException("Image size must be less than 3MB");
            }
            try {
                c.setImage(dto.getImage().getBytes());
                c.setImageName(dto.getImage().getOriginalFilename());
            } catch (Exception e) {
                throw new RuntimeException("Error processing image");
            }
        }

        Customer saved = customerRepository.save(c);

        return new CustomerResponseDTO(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getMobileNumber(),
                saved.getAge(),
                saved.getSex(),
                saved.getImageName()
        );
    }

    @Transactional(readOnly = true)
    public JwtResponseDTO login(CustomerLoginDTO dto) { 
        Customer c = customerRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Oops! Customer not registered. Please create an account."));

        if (!encoder.matches(dto.getPassword(), c.getPassword())) {
            throw new RuntimeException("Password does not match!");
        }

        String token = jwtUtil.generateToken(c.getEmail(), "CUSTOMER");

        CustomerResponseDTO customerData = new CustomerResponseDTO(
                c.getId(),
                c.getName(),
                c.getEmail(),
                c.getMobileNumber(),
                c.getAge(),
                c.getSex(),
                c.getImageName()
        );

        return new JwtResponseDTO(token, customerData);
    }
    
    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}