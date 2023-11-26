package com.example.demo.repository;

import com.example.demo.model.Participant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ParticipantRepo extends CrudRepository<Participant, Long> {
    @Query(value = """
    insert into participant(id, chat_allowed, video_allowed, voice_allowed, volume, user_id, participants_id)
    values (nextval('participant_id_seq'), 
        true, true, true, 100, :userId, :roomId)
    on conflict do nothing 
    returning *;
    """, nativeQuery = true)
    Participant insert(@Param("userId") Long userId, @Param("roomId") Long roomId);
}
