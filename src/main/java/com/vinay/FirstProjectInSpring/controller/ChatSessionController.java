package com.vinay.FirstProjectInSpring.controller;

import com.vinay.FirstProjectInSpring.model.ChatSession;
import com.vinay.FirstProjectInSpring.model.SessionStatus;
import com.vinay.FirstProjectInSpring.repository.ChatSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-session")
public class ChatSessionController {

    private final ChatSessionRepository sessionRepo;

    // Customer requests chat with a support agent
    @PostMapping("/request")
    public ChatSession requestChat(@RequestParam String customerId, @RequestParam String supportId) {
        ChatSession session = new ChatSession();
        session.setCustomerId(customerId);
        session.setSupportId(supportId);
        session.setStatus(SessionStatus.PENDING);
        return sessionRepo.save(session);
    }

    // Support accepts request
    @PostMapping("/accept/{id}")
    public ChatSession acceptChat(@PathVariable Long id) {
        ChatSession session = sessionRepo.findById(id).orElseThrow();
        session.setStatus(SessionStatus.ACTIVE);
        return sessionRepo.save(session);
    }

    // Support rejects request
    @PostMapping("/reject/{id}")
    public ChatSession rejectChat(@PathVariable Long id) {
        ChatSession session = sessionRepo.findById(id).orElseThrow();
        session.setStatus(SessionStatus.REJECTED);
        return sessionRepo.save(session);
    }

    // Customer checks their session
    @GetMapping("/customer/{customerId}")
    public List<ChatSession> getCustomerSessions(@PathVariable String customerId) {
        return sessionRepo.findByCustomerIdAndStatus(customerId, SessionStatus.PENDING);
    }

    // Support sees pending requests
    @GetMapping("/support/{supportId}/pending")
    public List<ChatSession> getPendingRequests(@PathVariable String supportId) {
        return sessionRepo.findBySupportIdAndStatus(supportId, SessionStatus.PENDING);
    }
}
