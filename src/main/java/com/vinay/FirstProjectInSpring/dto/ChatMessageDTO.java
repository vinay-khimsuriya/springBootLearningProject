package com.vinay.FirstProjectInSpring.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChatMessageDTO {
    private String senderId;
    private String receiverId;
    private String message;
    private LocalDateTime timestamp;
}
