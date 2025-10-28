package com.example.SeetSelectUser.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.SeetSelectUser.Entity.User;
import com.example.SeetSelectUser.Mapper.Table.UserMapper;
import com.example.SeetSelectUser.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    // 获取所有用户
    public List<User> getAllUser() {
        return this.list();
    }
}