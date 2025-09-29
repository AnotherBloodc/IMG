package com.example.SeetLeft.Mapper.Impl;

import com.example.SeetLeft.Entity.User;
import com.example.SeetLeft.Mapper.UserStyleMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserStyleMapperImpl {

    @Resource
    private UserStyleMapper userStyleMapper;

    public List<User> select(User user) {
        return userStyleMapper.select(user);
    }
}
