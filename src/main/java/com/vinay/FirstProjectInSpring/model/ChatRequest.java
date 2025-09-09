package com.vinay.FirstProjectInSpring.model;

import javax.persistence.*;

@Entity
@Table(name = "chat_requests")
public class ChatRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private Long supportId; 

    @Enumerated(EnumType.STRING)
    private RequestStatus status; 

    private boolean active;

    public ChatRequest() {}

    public ChatRequest(Long id, Long customerId, Long supportId, RequestStatus status, boolean active) {
        this.id = id;
        this.customerId = customerId;
        this.supportId = supportId;
        this.status = status;
        this.active = active;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getSupportId() { return supportId; }
    public void setSupportId(Long supportId) { this.supportId = supportId; }

    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
