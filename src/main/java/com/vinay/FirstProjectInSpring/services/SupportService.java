package com.vinay.FirstProjectInSpring.services;

import com.vinay.FirstProjectInSpring.dto.*;
import com.vinay.FirstProjectInSpring.model.Support;
import com.vinay.FirstProjectInSpring.repository.SupportRepository;
import com.vinay.FirstProjectInSpring.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupportService {
    private final SupportRepository supportRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public SupportService(SupportRepository supportRepository, JwtUtil jwtUtil) {
        this.supportRepository = supportRepository;
        this.jwtUtil = jwtUtil;
    }

    // ---------------- Register ----------------
    @Transactional
    public SupportResponseDTO register(SupportDTO dto) {
        if (supportRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Supporter with this email already exists!");
        }

        Support s = new Support();
        s.setName(dto.getName());
        s.setEmail(dto.getEmail());
        s.setDesignation(dto.getDesignation());
        s.setStatus(dto.getStatus() != null ? dto.getStatus() : false);
        s.setIsAvailable(dto.getIsAvailable() != null ? dto.getIsAvailable() : false);
        s.setPassword(encoder.encode(dto.getPassword()));
        s.setImageName(dto.getImageName());
        s.setImagePath(dto.getImagePath());

        Support saved = supportRepository.save(s);

        return new SupportResponseDTO(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getDesignation(),
                saved.getStatus(),
                saved.getIsAvailable(),
                saved.getPassword(),
                saved.getImageName(),
                saved.getImagePath()
        );
    }

    // ---------------- Login ----------------
    @Transactional
    public JwtSupportResponseDTO login(SupportLoginDTO dto) {
        Support s = supportRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Support not found"));

        if (!encoder.matches(dto.getPassword(), s.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // On login → set status and isAvailable to true
        s.setStatus(true);
        s.setIsAvailable(true);
        supportRepository.save(s);

        SupportResponseDTO responseDTO = new SupportResponseDTO(
                s.getId(),
                s.getName(),
                s.getEmail(),
                s.getDesignation(),
                s.getStatus(),
                s.getIsAvailable(),
                s.getPassword(),
                s.getImageName(),
                s.getImagePath()
        );

        return new JwtSupportResponseDTO(jwtUtil.generateToken(s.getEmail(), "SUPPORT"), responseDTO);
    }

    // ---------------- Update Status ----------------
    @Transactional
    public void updateStatus(Long supportId, boolean status) {
        Support s = supportRepository.findById(supportId)
                .orElseThrow(() -> new RuntimeException("Support not found"));
        s.setStatus(status);

        if (!status) {
            s.setIsAvailable(false); // offline → cannot be available
        } else if (s.getIsAvailable() == null || !s.getIsAvailable()) {
            s.setIsAvailable(true); // online and free
        }

        supportRepository.save(s);
    }

    // ---------------- Update Availability ----------------
    @Transactional
    public void updateAvailability(Long supportId, boolean available) {
        Support s = supportRepository.findById(supportId)
                .orElseThrow(() -> new RuntimeException("Support not found"));
        if (!s.getStatus()) {
            s.setIsAvailable(false); // offline → force unavailable
        } else {
            s.setIsAvailable(available);
        }
        supportRepository.save(s);
    }

    // ---------------- Fetch Available Supports ----------------
    @Transactional(readOnly = true)
    public List<SupportResponseDTO> getAvailableSupports() {
        return supportRepository.findByStatusTrueAndIsAvailableTrue()
                .stream()
                .map(s -> new SupportResponseDTO(
                        s.getId(),
                        s.getName(),
                        s.getEmail(),
                        s.getDesignation(),
                        s.getStatus(),
                        s.getIsAvailable(),
                        null,
                        s.getImageName(),
                        s.getImagePath()
                ))
                .collect(Collectors.toList());
    }
}
