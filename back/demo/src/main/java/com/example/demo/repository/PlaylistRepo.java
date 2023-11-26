package com.example.demo.repository;

import com.example.demo.model.Playlist;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlaylistRepo extends CrudRepository<Playlist, Long> {
    Optional<Playlist> findByName(String name);
}
