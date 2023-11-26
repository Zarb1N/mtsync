package com.example.demo.controller;

import com.example.demo.model.SyncMessage;
import com.example.demo.service.RoomSyncService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SessionStateController {
    private RoomSyncService roomSyncService;

    @GetMapping("/sessions/{sessionId}")
    public SyncMessage getSessionState(@PathVariable String sessionId) {
        return roomSyncService.getLastStateRenewed(sessionId).get();
    }
}
