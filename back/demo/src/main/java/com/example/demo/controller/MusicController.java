package com.example.demo.controller;

import com.example.demo.model.Playlist;
import com.example.demo.model.Track;
import com.example.demo.repository.PlaylistRepo;
import com.example.demo.repository.TrackRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.notFound;


@RestController
@RequiredArgsConstructor
@Slf4j
public class MusicController {
    CacheControl cacheControl = CacheControl.maxAge(1, TimeUnit.DAYS)
            .noTransform()
            .mustRevalidate();

    @Value("${app.sound.path}")
    Path SOUND_PATH;

    final PlaylistRepo playlistRepo;
    final TrackRepo trackRepo;

    @GetMapping("/sound/list")
    public List<String> listSounds() throws IOException {
        return Files.list(SOUND_PATH)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
    }

    @GetMapping("/music/playlists")
    public Iterable<Playlist> listPlaylists() {
        return playlistRepo.findAll();
    }

    @GetMapping("/music/playlists/{id}")
    public ResponseEntity<Playlist> getPlaylist(@PathVariable Long id) {
        return playlistRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @GetMapping("/music/tracks/{id}")
    public ResponseEntity<Track> getTrack(@PathVariable Long id) {
        return trackRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @GetMapping("/music/tracks")
    public Iterable<Track> getTracks() {
        return trackRepo.findAll();
    }

    @GetMapping("/music/tracks/{id}/play")
    @ResponseBody
    @Cacheable("sound")
    public ResponseEntity<FileSystemResource> play(@PathVariable Long id,
                                                   @RequestHeader("Range")  Optional<String> range) {
        log.info("Play called");
        Optional<Track> trackOpt = trackRepo.findById(id);
        if (trackOpt.isEmpty()) {
            return notFound().build();
        }

        Track track = trackOpt.get();
        Path path = SOUND_PATH.resolve(track.getFileName());
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "media/mpeg3");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .cacheControl(cacheControl).body(new FileSystemResource(path));
    }

    @GetMapping(value = "/sound")
    @ResponseBody
    public ResponseEntity<FileSystemResource> stream(@RequestParam String name) {
        Path path = SOUND_PATH.resolve(name);
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "media/mpeg3");
        return new ResponseEntity<>(new FileSystemResource(path), responseHeaders, HttpStatus.OK);
    }


}
