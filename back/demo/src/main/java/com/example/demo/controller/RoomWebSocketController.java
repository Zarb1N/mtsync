package com.example.demo.controller;

import com.example.demo.model.SyncMessage;
import com.example.demo.service.RoomSyncService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
@Slf4j
public class RoomWebSocketController {
    private SimpMessagingTemplate simpMessagingTemplate;
    private RoomSyncService roomSyncService;

    @MessageMapping("/sync")
    public void processSync(@Payload SyncMessage syncMessage) {
        roomSyncService.updateState(syncMessage);
        simpMessagingTemplate.convertAndSendToUser(
                syncMessage.getSessionId(),
                "/queue/sync",
                syncMessage
        );
    }
}
