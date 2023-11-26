package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Table(name = "users")
@Entity
@Getter
@Setter
@ToString
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User {
    @Id
    @GeneratedValue
    protected Long id;

    @Column(columnDefinition = "text", unique = true, nullable = false)
    protected String email;

    @Column(columnDefinition = "text", unique = true, nullable = false)
    protected String username;

    @Column(columnDefinition = "text", nullable = false)
    @JsonIgnore
    protected String password;

    @Column(columnDefinition = "text")
    protected String avatarUrl;

    public User(String email, String username, String password, String avatarUrl) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.avatarUrl = avatarUrl;
    }

    public User() {}

}
