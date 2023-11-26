package com.example.demo.controller;

import com.example.demo.entity.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
@AllArgsConstructor
@Slf4j
public class ChatWebSocketController {

    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        log.info("Received: " + chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.session(),
                "/queue/messages",
                chatMessage
        );
    }
}
