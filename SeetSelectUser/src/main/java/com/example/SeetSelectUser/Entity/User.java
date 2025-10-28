package com.example.SeetSelectUser.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(columnDefinition = "datetime default current_timestamp()")
    private LocalDateTime datetime;

    @Column(nullable = false)
    private Boolean effect;

    @TableField(exist = false)
    @Transient
    private List<UserStyle> userStyle;

}
