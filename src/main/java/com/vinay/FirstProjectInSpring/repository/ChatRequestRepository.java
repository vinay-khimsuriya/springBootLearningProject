package com.vinay.FirstProjectInSpring.repository;

import com.vinay.FirstProjectInSpring.model.ChatRequest;
import com.vinay.FirstProjectInSpring.model.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRequestRepository extends JpaRepository<ChatRequest, Long> {
    Optional<ChatRequest> findByCustomerIdAndActiveTrue(Long customerId);

    List<ChatRequest> findByStatus(RequestStatus status);

    List<ChatRequest> findBySupportIdAndActiveTrue(Long supportId);
}
