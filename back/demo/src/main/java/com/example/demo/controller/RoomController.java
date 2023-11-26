package com.example.demo.controller;

import com.example.demo.dto.CreateRoomRequest;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.RoomSyncService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RoomController {
    final RoomSyncService roomSyncService;
    final RoomRepo roomRepo;
    final UserRepo userRepo;
    final PlaylistRepo playlistRepo;
    final ParticipantRepo participantRepo;

    @GetMapping("/rooms")
    public Iterable<Room> listRooms(@RequestParam Optional<String> query) {
        if (query.isEmpty()) {
            return roomRepo.findAll();
        }

        return roomRepo.search(query.get());
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable String id) {
        Optional<Room> room = StringUtils.isNumeric(id)
                ? roomRepo.findById(Long.parseLong(id))
                : roomRepo.findBySessionId(id);

        return room.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/rooms")
    public Room createRoom(@RequestBody CreateRoomRequest createRoomRequest, Principal principal) {
        Participant participant = new Participant();
        participant.setUser(userRepo.findByUsername(principal.getName()).get());

        participantRepo.save(participant);

        Room room = new Room();
        room.setSessionId(createRoomRequest.getSessionId());
        room.setName(createRoomRequest.getName());
        Playlist playlist = playlistRepo.findById(createRoomRequest.getPlaylistId()).get();
        room.setPlaylist(playlist);
        room.setHost(participant);
        room.setPreviewUrl(createRoomRequest.getPreviewUrl());

        roomRepo.save(room);

        roomSyncService.initState(createRoomRequest.getSessionId(), playlist.getTracks().get(0).getId());

        return room;
    }

    @GetMapping("/rooms/{sessionId}/join")
    public ResponseEntity<SyncMessage> joinRoom(@PathVariable String sessionId, Principal principal) {
        User user = userRepo.findByUsername(principal.getName()).get();

        Optional<Room> roomOpt = roomRepo.findBySessionId(sessionId);
        if (roomOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Room room = roomOpt.get();

        participantRepo.insert(user.getId(), roomOpt.get().getId());

        return roomSyncService.getLastStateRenewed(sessionId)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
