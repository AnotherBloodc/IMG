package com.example.SeetLeft.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_style")
public class UserStyle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int userid;

    @Column(columnDefinition = "datetime default current_timestamp()")
    private LocalDateTime datetime;

    @Column(nullable = false)
    private String name;
}
