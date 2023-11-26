package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
public class Sync extends SyncMessage {
    Long receivedTs;

    public Sync(Event event, String sessionId, Long trackTsMs, Long trackId, Long receivedTs) {
        super(event, sessionId, trackTsMs, trackId);
        this.receivedTs = receivedTs;
    }

    public static Sync from(SyncMessage syncMessage) {
        return new Sync(syncMessage.event, syncMessage.sessionId,
                syncMessage.trackTsMs, syncMessage.trackId, Instant.now().toEpochMilli());
    }
}
