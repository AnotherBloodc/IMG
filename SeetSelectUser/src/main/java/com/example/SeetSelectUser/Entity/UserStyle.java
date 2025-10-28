package com.example.SeetSelectUser.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class UserStyle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String userid;

    @Column(nullable = false)
    private LocalDateTime datetime;

    @Column(nullable = false)
    private String name;

    @Transient
    private User user;
}
