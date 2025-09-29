package com.example.SeetLeft.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Entity.UserStyle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserStyleMapper extends BaseMapper<UserStyle> {

    @Select("select * from `Seetpro`.`User`")
    List<User> select(User user);
}
