package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Room {
    @Id @GeneratedValue
    Long id;

    @Column(columnDefinition = "text", unique = true)
    String sessionId;

    @Column(columnDefinition = "text")
    String name;

    @Column(columnDefinition = "text")
    String previewUrl;

    @JoinColumn @ManyToOne
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    Participant host;

    @JoinColumn @OneToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    Set<Participant> participants = Set.of();

    @JoinColumn @ManyToOne
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    Playlist playlist;
}
