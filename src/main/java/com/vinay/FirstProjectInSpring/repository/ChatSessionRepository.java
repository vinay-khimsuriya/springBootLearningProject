package com.vinay.FirstProjectInSpring.repository;

import com.vinay.FirstProjectInSpring.model.ChatSession;
import com.vinay.FirstProjectInSpring.model.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {
    List<ChatSession> findBySupportIdAndStatus(String supportId, SessionStatus status);
    List<ChatSession> findByCustomerIdAndStatus(String customerId, SessionStatus status);
}
