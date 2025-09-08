package com.vinay.FirstProjectInSpring.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderId;    
    private String receiverId;  
    private String message;

    private LocalDateTime timestamp = LocalDateTime.now();
}
