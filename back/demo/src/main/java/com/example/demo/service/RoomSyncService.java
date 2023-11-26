package com.example.demo.service;

import com.example.demo.model.Sync;
import com.example.demo.model.SyncMessage;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomSyncService {
    private Map<String, Sync> lastSessionUnitedState = new ConcurrentHashMap<>();

    public void updateState(SyncMessage syncMessage) {
        Sync sync = Sync.from(syncMessage);
        if (lastSessionUnitedState.containsKey(sync.getSessionId())) {
            SyncMessage prevSync = lastSessionUnitedState.get(sync.getSessionId());
            SyncMessage.Event event = sync.getEvent() == SyncMessage.Event.REWIND
                    ? prevSync.getEvent()
                    : sync.getEvent();
            lastSessionUnitedState.put(sync.getSessionId(),
                    new Sync(event, sync.getSessionId(), sync.getTrackTsMs(), sync.getTrackId(), sync.getReceivedTs()));
        } else {
            lastSessionUnitedState.put(syncMessage.getSessionId(), sync);
        }
    }

    public Sync initState(String sessionId, Long trackId) {
        Sync sync = new Sync(SyncMessage.Event.PAUSE, sessionId, 0L, trackId, Instant.now().toEpochMilli());
        lastSessionUnitedState.put(sync.getSessionId(), sync);
        return sync;
    }

    public Optional<SyncMessage> getLastStateRenewed(String sessionId) {
        Optional<Sync> syncOpt = Optional.ofNullable(lastSessionUnitedState.get(sessionId));

        return syncOpt.map(sync -> new SyncMessage(sync.getEvent(), sync.getSessionId(), sync.getTrackId(), sync.getEvent() == SyncMessage.Event.PAUSE
                ? sync.getTrackTsMs()
                : sync.getTrackTsMs() + Instant.now().toEpochMilli() - sync.getReceivedTs()
        ));
    }
}
