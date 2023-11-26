package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Getter
@Setter
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Track {
    @Id
    @GeneratedValue
    protected Long id;

    @Column(columnDefinition = "text")
    String name;

    @Column(columnDefinition = "text")
    String author;

    @Column(columnDefinition = "text")
    String previewUrl;

    @Column(columnDefinition = "text")
    String fileName;
}
