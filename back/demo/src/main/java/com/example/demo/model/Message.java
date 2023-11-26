package com.example.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.Instant;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Message {
    @Id @GeneratedValue
    Long id;

    @Column(columnDefinition = "text")
    String message;

    @Column
    Instant instant;

    @JoinColumn
    @ManyToOne
    Room room;
}
