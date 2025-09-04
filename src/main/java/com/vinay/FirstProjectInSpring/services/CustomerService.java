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
    private final FileStorageService fileStorageService;
    public CustomerService(CustomerRepository customerRepository, JwtUtil jwtUtil, FileStorageService fileStorageService) {
        this.customerRepository = customerRepository;
        this.jwtUtil = jwtUtil;
        this.fileStorageService = fileStorageService;
    }

    // ---------------- Registration ----------------
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
        c.setDesignation(dto.getDesignation());

        
        c.setImageName(dto.getImageName()); 
        c.setImagePath(dto.getImagePath()); 

        Customer saved = customerRepository.save(c);

        return new CustomerResponseDTO(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getMobileNumber(),
                saved.getAge(),
                saved.getDesignation(),
                saved.getSex(),
                saved.getImageName(),
                saved.getImagePath()
        );
    }

    

    // ---------------- Login ----------------
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
                c.getDesignation(),
                c.getSex(),
                c.getImageName(),
                c.getImagePath()
        );

        return new JwtResponseDTO(token, customerData);
    }

   
    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}
