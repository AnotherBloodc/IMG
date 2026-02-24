package com.example.SeetLeft.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.alibaba.fastjson2.annotation.JSONField;
import jakarta.persistence.*;
import lombok.Data;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "Username", nullable = false)
    private String username;

    @JSONField(serialize = false)
    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Datetime", nullable = false, columnDefinition = "datetime default current_timestamp()")
    private LocalDateTime datetime;

    @Column(name = "Effect", nullable = false, columnDefinition = "boolean default true")
    private Boolean effect;

    @Column(name = "Role", nullable = false)
    private String role;

    @TableField(exist = false)
    @Transient
    private List<UserStyle> userStyle;

    @TableField(exist = false)
    @Transient
    private String token;
}
//@Id：表示这个属性是主键，作用是生成主键值，如果是数字类型，会自动增长，如果为空，会自动生成一个UUID
//@Column：设置这个属性对应数据库中的哪个字段，nullable = false表示这个字段不能为空
//@Transient：标识一个数据库没有的字段，仅对JPA有效，MyBatis Plus不识别
//@TableField(exist = false)：仅对MyBatis Plus有效，exist = false表示这个字段不存在数据库中
//@TableField(exist = true)：默认值，仅对MyBatis Plus有效，表示字段存在于数据库中
//@Table(name = "user")：告诉mybatisPlus，这个类对应数据库中的哪个表