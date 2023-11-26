package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

@Entity
@Getter
@Setter
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Playlist {
    @Id @GeneratedValue
    Long id;

    @Column(columnDefinition = "text")
    String name;

    @OneToMany @JoinColumn
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    List<Track> tracks;
}
