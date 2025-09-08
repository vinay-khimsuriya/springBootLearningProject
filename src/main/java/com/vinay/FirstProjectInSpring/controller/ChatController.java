package com.vinay.FirstProjectInSpring.controller;
import com.vinay.FirstProjectInSpring.dto.ChatMessageDTO;
import com.vinay.FirstProjectInSpring.model.ChatMessage;
import com.vinay.FirstProjectInSpring.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatRepo;

    
    @MessageMapping("/sendMessage")
    public void sendMessage(ChatMessageDTO messageDTO) {
        ChatMessage chat = new ChatMessage();
        chat.setSenderId(messageDTO.getSenderId());
        chat.setReceiverId(messageDTO.getReceiverId());
        chat.setMessage(messageDTO.getMessage());

        chatRepo.save(chat);

        // Send to specific user
        messagingTemplate.convertAndSendToUser(
                messageDTO.getReceiverId(),
                "/queue/messages",
                messageDTO
        );
    }

 
    @GetMapping("/chat/history/{senderId}/{receiverId}")
    public List<ChatMessage> getChatHistory(@PathVariable String senderId, @PathVariable String receiverId) {
        List<ChatMessage> chats1 = chatRepo.findBySenderIdAndReceiverIdOrderByTimestamp(senderId, receiverId);
        List<ChatMessage> chats2 = chatRepo.findByReceiverIdAndSenderIdOrderByTimestamp(senderId, receiverId);
        chats1.addAll(chats2);
        return chats1;
    }
}
