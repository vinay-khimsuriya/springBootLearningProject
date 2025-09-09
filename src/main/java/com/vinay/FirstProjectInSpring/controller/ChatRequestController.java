package com.vinay.FirstProjectInSpring.controller;

import com.vinay.FirstProjectInSpring.dto.ChatRequestDTO;
import com.vinay.FirstProjectInSpring.services.ChatRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class ChatRequestController {

    private final ChatRequestService service;

    public ChatRequestController(ChatRequestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ChatRequestDTO> createRequest(@RequestParam Long customerId) {
        return ResponseEntity.ok(service.createRequest(customerId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ChatRequestDTO> getCustomerRequest(@PathVariable Long customerId) {
        return ResponseEntity.ok(service.getCustomerRequest(customerId));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ChatRequestDTO>> getPendingRequests() {
        return ResponseEntity.ok(service.getPendingRequests());
    }

    @PutMapping("/cancel/{customerId}")
    public ResponseEntity<String> cancelRequest(@PathVariable Long customerId) {
        service.cancelRequest(customerId);
        return ResponseEntity.ok("Request cancelled");
    }
}
