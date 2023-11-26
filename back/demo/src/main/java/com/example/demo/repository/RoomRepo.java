package com.example.demo.repository;

import com.example.demo.model.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepo extends CrudRepository<Room, Long> {
    Optional<Room> findBySessionId(String sessionId);

    @Query(value = """
    select * from room
             join public.playlist p on p.id = room.playlist_id
             join public.track t on p.id = t.tracks_id
    where room.name % ?1 or room.name ilike '%' || ?1 || '%'
       or p.name % ?1 or p.name ilike '%' || ?1 || '%'
       or t.name % ?1 or t.name ilike '%' || ?1 || '%'
       or t.author % ?1 or t.author ilike '%' || ?1 || '%'
    order by (room.preview_url <-> ?1) + (p.name <-> ?1) + (t.name <-> ?1) + (t.author <-> ?1)
    """, nativeQuery = true)
    List<Room> search(String query);
}
