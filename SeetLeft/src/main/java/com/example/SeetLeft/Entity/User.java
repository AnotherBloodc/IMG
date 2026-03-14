package com.example.SeetLeft.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {


    @Id
    @TableId(type = IdType.AUTO)
    @Column(name = "id")
    private Integer id;

    @TableField("name")
    @Column(name = "name")
    private String name;

    @TableField("username")
    @Column(name = "username")
    private String username;

    @TableField("password")
    @Column(name = "password")
    @JSONField(serialize = false)
    private String password;

    @TableField("phone")
    @Column(name = "phone")
    private String phone;

    @TableField("datetime")
    @Column(name = "datetime", columnDefinition = "datetime default current_timestamp()")
    private LocalDateTime datetime;

    @TableField("effect")
    @Column(name = "effect", columnDefinition = "boolean default true")
    private Boolean effect;

    @TableField("roleID")
    @Column(name = "roleID")
    private String roleID;

    @TableField(exist = false)
    @Transient
    private List<UserStyle> userStyle;

    @TableField(exist = false)
    @Transient
    private String token;
}
//@Id：表示这个属性是主键，作用是生成主键值，如果是数字类型，会自动增长，如果为空，会自动生成一个UUID

//@JSONField(serialize = false): 表示这个属性不参与序列化

//@Column：设置这个属性对应数据库中的哪个字段
//  nullable = false：表示这个字段不能为空

//@Transient：标识一个数据库没有的字段，仅对JPA有效，MyBatis Plus不识别

//@TableField：指定此字段对应的数据库名字，仅对MyBatis Plus有效，JPA不识别
//  exist = false：表示这个字段不存在数据库中

//@Table(name = "user")：告诉mybatisPlus，这个类对应数据库中的哪个表