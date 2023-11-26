package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Getter
@Setter
@ToString
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Participant  {
    @Id
    @GeneratedValue
    protected Long id;

    @Column
    protected Boolean voiceAllowed = true;

    @Column
    protected Boolean videoAllowed = true;

    @Column
    protected Boolean chatAllowed = true;

    @Column
    protected int volume = 100;

    @JoinColumn @ManyToOne
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    User user;
}
