package com.vinay.FirstProjectInSpring.services;

import com.vinay.FirstProjectInSpring.dto.*;
import com.vinay.FirstProjectInSpring.model.Support;
import com.vinay.FirstProjectInSpring.repository.SupportRepository;
import com.vinay.FirstProjectInSpring.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SupportService {
    private final SupportRepository supportRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public SupportService(SupportRepository supportRepository, JwtUtil jwtUtil) {
        this.supportRepository = supportRepository;
        this.jwtUtil = jwtUtil;
    }

    public SupportResponseDTO register(SupportDTO dto) {
  if (supportRepository.findByEmail(dto.getEmail()).isPresent()) {
        throw new RuntimeException("Support with this email already exists!");
    }

        Support s = new Support();
        s.setName(dto.getName());
        s.setEmail(dto.getEmail());
        s.setDesignation(dto.getDesignation());
        s.setAvailability(dto.getAvailability() != null ? dto.getAvailability() : true);
        s.setStatus(dto.getStatus() != null ? dto.getStatus() : "offline");
        s.setPassword(encoder.encode(dto.getPassword()));

        Support saved = supportRepository.save(s);

        return new SupportResponseDTO(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getDesignation(),
                saved.getAvailability(),
                saved.getStatus()
        );
    }

    

   public JwtSupportResponseDTO login(SupportLoginDTO dto) {
    Support s = supportRepository.findByEmail(dto.getEmail())
            .orElseThrow(() -> new RuntimeException("Support not found"));

    if (!encoder.matches(dto.getPassword(), s.getPassword())) {
        throw new RuntimeException("Invalid password");
    }

    SupportResponseDTO responseDTO = new SupportResponseDTO(
            s.getId(), s.getName(), s.getEmail(), s.getDesignation(), s.getAvailability(), s.getStatus()
    );

    return new JwtSupportResponseDTO(jwtUtil.generateToken(s.getEmail(), "SUPPORT"), responseDTO);
}

}
