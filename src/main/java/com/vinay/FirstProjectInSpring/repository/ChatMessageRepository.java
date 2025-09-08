package com.vinay.FirstProjectInSpring.repository;

import com.vinay.FirstProjectInSpring.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySenderIdAndReceiverIdOrderByTimestamp(String senderId, String receiverId);
    List<ChatMessage> findByReceiverIdAndSenderIdOrderByTimestamp(String receiverId, String senderId);
}
