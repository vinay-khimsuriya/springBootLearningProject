package com.vinay.FirstProjectInSpring.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class ChatSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerId;
    private String supportId;

    @Enumerated(EnumType.STRING)
    private SessionStatus status = SessionStatus.PENDING; // PENDING, ACTIVE, REJECTED

    private LocalDateTime createdAt = LocalDateTime.now();
}
