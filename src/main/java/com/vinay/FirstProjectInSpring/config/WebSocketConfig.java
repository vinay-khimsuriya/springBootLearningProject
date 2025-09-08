package com.vinay.FirstProjectInSpring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Register STOMP endpoints (used for handshake between frontend and backend)
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat")
                .setAllowedOrigins("http://127.0.0.1:5500", "http://localhost:5500")
                .withSockJS(); // fallback for browsers that don’t support WebSocket
    }

    // Configure message broker (who sends/receives messages)
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");  // chat messages sent to subscribers
        registry.setApplicationDestinationPrefixes("/app"); // for messages coming from client
    }
}
