package com.example.demo.model;


import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SyncMessage {
    Event event;
    String sessionId;
    Long trackTsMs;
    Long trackId;

    public enum Event { PAUSE, PLAY, REWIND }
}