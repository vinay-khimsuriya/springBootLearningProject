package com.vinay.FirstProjectInSpring.repository;

import com.vinay.FirstProjectInSpring.model.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SupportRepository extends JpaRepository<Support, Long> {
    Optional<Support> findByEmail(String email);
}
