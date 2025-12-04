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
@Table(name = "user")//告诉mybatisPlus，这个类对应数据库中的哪个表

public class User implements Serializable {

    //@Id:表示这个属性是主键，作用是生成主键值，如果是数字类型，会自动增长，如果为空，会自动生成一个UUID
    //@Column:设置这个属性对应数据库中的哪个字段，nullable = false表示这个字段不能为空
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @JSONField(serialize = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(columnDefinition = "datetime default current_timestamp()")
    private LocalDateTime datetime;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean effect;

    @Column(nullable = false, columnDefinition = "1")
    private String role;

    //Transient:标识一个数据库没有的字段，定义后MybatisPlus和JPA都不会去数据库中查询这个字段
    @TableField(exist = false)
    @Transient
    private List<UserStyle> userStyle;
}