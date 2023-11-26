package com.example.demo;

import com.example.demo.model.SyncMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import java.lang.reflect.Type;

@Slf4j
public class MyStompSessionHandler implements StompSessionHandler {
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("Connected...");
        session.subscribe("/user/123/queue/sync", this);
        log.info("Subscribed...");

//        session.send("/sync", new SyncMessage(
//                SyncMessage.Event.REWIND,
//                "123", 1L));
//        session.send("/sync", new SyncMessage(
//                SyncMessage.Event.PAUSE,
//                "123", 3L));
//        session.send("/sync",
//                new SyncMessage(SyncMessage.Event.PLAY, "123", 5L));
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {

    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {

    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return SyncMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        SyncMessage message = (SyncMessage) payload;
        try {
            log.info("Received: " + new ObjectMapper().writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
