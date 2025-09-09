package com.vinay.FirstProjectInSpring.services;

import com.vinay.FirstProjectInSpring.dto.ChatRequestDTO;
import com.vinay.FirstProjectInSpring.model.ChatRequest;
import com.vinay.FirstProjectInSpring.model.RequestStatus;
import com.vinay.FirstProjectInSpring.model.Customer;
import com.vinay.FirstProjectInSpring.model.Support;
import com.vinay.FirstProjectInSpring.repository.ChatRequestRepository;
import com.vinay.FirstProjectInSpring.repository.CustomerRepository;
import com.vinay.FirstProjectInSpring.repository.SupportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatRequestService {

    private final ChatRequestRepository chatRequestRepository;
    private final CustomerRepository customerRepository;
    private final SupportRepository supportRepository;

    public ChatRequestService(ChatRequestRepository chatRequestRepository,
                              CustomerRepository customerRepository,
                              SupportRepository supportRepository) {
        this.chatRequestRepository = chatRequestRepository;
        this.customerRepository = customerRepository;
        this.supportRepository = supportRepository;
    }

    @Transactional
    public ChatRequestDTO createRequest(Long customerId) {
        chatRequestRepository.findByCustomerIdAndActiveTrue(customerId)
                .ifPresent(r -> { throw new RuntimeException("Active request already exists!"); });

        ChatRequest request = new ChatRequest();
        request.setCustomerId(customerId);
        request.setStatus(RequestStatus.PENDING);
        request.setActive(true);

        ChatRequest saved = chatRequestRepository.save(request);

        Customer c = customerRepository.findById(customerId).orElseThrow();
        return new ChatRequestDTO(saved.getId(), saved.getCustomerId(), null,
                saved.getStatus(), saved.isActive(), c.getName(), null);
    }

    @Transactional(readOnly = true)
    public ChatRequestDTO getCustomerRequest(Long customerId) {
        return chatRequestRepository.findByCustomerIdAndActiveTrue(customerId)
                .map(r -> {
                    Customer c = customerRepository.findById(r.getCustomerId()).orElseThrow();
                    Support s = r.getSupportId() != null ? supportRepository.findById(r.getSupportId()).orElse(null) : null;
                    return new ChatRequestDTO(r.getId(), r.getCustomerId(), r.getSupportId(),
                            r.getStatus(), r.isActive(),
                            c.getName(), s != null ? s.getName() : null);
                })
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<ChatRequestDTO> getPendingRequests() {
        return chatRequestRepository.findByStatus(RequestStatus.PENDING)
                .stream()
                .map(r -> {
                    Customer c = customerRepository.findById(r.getCustomerId()).orElseThrow();
                    return new ChatRequestDTO(r.getId(), r.getCustomerId(), null,
                            r.getStatus(), r.isActive(), c.getName(), null);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelRequest(Long customerId) {
        ChatRequest request = chatRequestRepository.findByCustomerIdAndActiveTrue(customerId)
                .orElseThrow(() -> new RuntimeException("No active request found"));
        request.setStatus(RequestStatus.CANCELED);
        request.setActive(false);
        chatRequestRepository.save(request);
    }
}
