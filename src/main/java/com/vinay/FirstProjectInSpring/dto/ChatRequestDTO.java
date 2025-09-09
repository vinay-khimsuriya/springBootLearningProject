package com.vinay.FirstProjectInSpring.dto;

import com.vinay.FirstProjectInSpring.model.RequestStatus;

public class ChatRequestDTO {
    private Long id;
    private Long customerId;
    private Long supportId;
    private RequestStatus status;
    private boolean active;
    private String customerName;
    private String supportName;

    public ChatRequestDTO() {}

    public ChatRequestDTO(Long id, Long customerId, Long supportId,
                          RequestStatus status, boolean active,
                          String customerName, String supportName) {
        this.id = id;
        this.customerId = customerId;
        this.supportId = supportId;
        this.status = status;
        this.active = active;
        this.customerName = customerName;
        this.supportName = supportName;
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

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getSupportName() { return supportName; }
    public void setSupportName(String supportName) { this.supportName = supportName; }
}
