package com.example.SeetSelectUser.Mapper.Table;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.SeetSelectUser.Entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
