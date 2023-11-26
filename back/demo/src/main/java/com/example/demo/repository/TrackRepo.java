package com.example.demo.repository;

import com.example.demo.model.Track;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TrackRepo extends CrudRepository<Track, Long> {
    Optional<Track> findByName(String name);
}
