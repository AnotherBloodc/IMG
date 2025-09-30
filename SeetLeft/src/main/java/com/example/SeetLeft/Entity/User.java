package com.example.SeetLeft.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "user")//告诉mybatisPlus，这个类对应数据库中的哪个表
public class User {

    //@Id:表示这个属性是主键，作用是生成主键值，如果是数字类型，会自动增长，如果为空，会自动生成一个UUID
    //@Column:设置这个属性对应数据库中的哪个字段，nullable = false表示这个字段不能为空
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

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean effect;

    //Transient:标识一个数据库没有的字段，定义后MybatisPlus就不会去数据库中查询这个字段
    @Transient
    private List<UserStyle> userStyle;
}